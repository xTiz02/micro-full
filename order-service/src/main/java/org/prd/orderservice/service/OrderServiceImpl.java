package org.prd.orderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.prd.orderservice.model.dto.*;
import org.prd.orderservice.model.entity.OrderEntity;
import org.prd.orderservice.model.entity.OrderItem;
import org.prd.orderservice.model.repository.OrderRepository;
import org.prd.orderservice.util.OrderMapper;
import org.prd.orderservice.util.OrderStatus;
import org.prd.orderservice.util.Util;
import org.prd.orderservice.web.exception.PaymentException;
import org.prd.orderservice.web.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepo;
    private final PaymentFeignService payFeign;
    private final BookFeignService bookFeign;
    private final UserFeignService userFeign;

    public OrderServiceImpl(OrderRepository orderRepo, PaymentFeignService paymentFeignService, BookFeignService bookFeign, UserFeignService userFeign) {
        this.orderRepo = orderRepo;
        this.payFeign = paymentFeignService;
        this.bookFeign = bookFeign;
        this.userFeign = userFeign;
    }

    @Override
    @Transactional
    public ApiResponse createOrder(OrderRequest orderRequest){

        //Verificar si el usuario existe y traer sus datos de cuenta
        UserDto userDto = null;
        try{
            userDto = userFeign.getUserByUUID(orderRequest.userid());
        }catch (FeignException e){
            if(e.responseBody().isPresent()){
                ApiResponse apiResponse = Util.getClassFromBytes(e.responseBody().get().array(), ApiResponse.class);
                throw new ResourceNotFoundException(apiResponse.message());
            }
        }

        //Se podría verificar si el usuario puede realizar la compra o no
        //UserDto userDto = Util.getClassFromJson(userRes.getBody(), UserDto.class);
        String email = userDto.email();


        //Verificar si los libros existen y traer su precio actual
        String[] booksId = orderRequest.items().toArray(new String[0]);
        List<BookDto> bookItems = null;
        try{
            bookItems =  bookFeign.getBooksPrice(booksId);
        }catch (FeignException e){
            if(e.responseBody().isPresent()){
                ApiResponse apiResponse = Util.getClassFromBytes(e.responseBody().get().array(), ApiResponse.class);
                throw new ResourceNotFoundException(apiResponse.message());
            }
        }



        OrderEntity order = new OrderEntity();
        order.setOrderNum(UUID.randomUUID());
        order.setEmail(email);
        order.setUserUUID(orderRequest.userid());
        //order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        //List<BookDto> bookItems = Util.getListFromJson(bookRes.getBody(), BookDto.class);
        OrderEntity finalOrder = order;
        Set<OrderItem> orderItems = bookItems.stream()
                .map(OrderMapper::toOrderItem)
                .peek(orderItem -> orderItem.setOrder(finalOrder)) // Relación inversa aquí
                .collect(Collectors.toSet());
        BigDecimal total = orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        finalOrder.setTotal(total);
        finalOrder.setItems(orderItems);


        order = orderRepo.save(finalOrder);
        String message = String.format("Order with code %s and total %s saved by user %s",
                order.getOrderNum(),order.getTotal().toString() ,order.getUserUUID());
        return new ApiResponse(message, true);
    }

    @Override
    @Transactional
    public ApiResponse payOrder(UUID orderCode, String userPayData) {

        OrderEntity orderEntity = orderRepo.findByOrderNum(orderCode)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if(orderEntity.getStatus() != OrderStatus.PENDING){
            throw new PaymentException("Order already processed");
        }

        PaymentRequest paymentRequest = new PaymentRequest(orderEntity.getOrderNum(), orderEntity.getTotal(),orderEntity.getEmail(), userPayData);
        PaymentDto paymentDto = null;
        try {
            paymentDto = payFeign.processOrderPayment(paymentRequest);
        } catch (FeignException e) {
            if(e.responseBody().isPresent()){
                ApiResponse apiResponse = Util.getClassFromBytes(e.responseBody().get().array(), ApiResponse.class);
                throw new PaymentException(apiResponse.message());
            }
        }

        switch (paymentDto.paymentStatus()) {
        case SUCCESS:
            changeStateOrder(orderCode, OrderStatus.COMPLETED);
            break;
        case FAILED:
            changeStateOrder(orderCode, OrderStatus.CANCELED);
            break;
        default:
            break;
    }
    String message = String.format("Payment for order %s processed successfully", orderCode);
                return new ApiResponse(message, true);
    }

    @Override
    @Transactional
    public ApiResponse changeStateOrder(UUID orderCode,OrderStatus orderStatus) {
        OrderEntity orderEntity = orderRepo.findByOrderNum(orderCode)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        orderEntity.setStatus(orderStatus);
        orderEntity.setUpdatedAt(LocalDateTime.now());
        orderRepo.save(orderEntity);
        String message = String.format("Order state with code %s change", orderEntity.getOrderNum());
        return new ApiResponse(message, true);
    }

    @Override
    public Page<OrderDto> getOrdersByUser(UUID userId, int page, int size, String sort, String field) {
        if(Util.isValidField(field) && Util.isValidSort(sort)){
            Sort directionSort = sort.equalsIgnoreCase("asc")?
                    Sort.by(field).ascending():
                    Sort.by(field).descending();
            Pageable pageable = PageRequest.of(page, size, directionSort);
            return orderRepo.findByUserUUID(userId,pageable).map(OrderMapper::toDto);
        }else{
            Sort directionSort = Sort.by("id").ascending();
            return orderRepo.findByUserUUID(userId,PageRequest.of(page, size, directionSort))
                    .map(OrderMapper::toDto);
        }
    }

    @Override
    public OrderDto getOrderByNum(UUID orderCode) {
        return orderRepo.findByOrderNum(orderCode)
                .map(OrderMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    /*public void changeStateOrder(UUID orderCode, OrderStatus orderStatus) {
        OrderEntity orderEntity = orderRepo.findByOrderNum(orderCode)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        orderRepo.updateOrderStatus(orderEntity.getOrderNum(),orderStatus);
    }*/


}