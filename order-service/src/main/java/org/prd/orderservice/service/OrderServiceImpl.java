package org.prd.orderservice.service;

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
    public ApiResponse createOrder(OrderRequest orderRequest) {

        //Verificar si el usuario existe y traer sus datos de cuenta
        ResponseEntity<?> userRes = userFeign.getUserByUUID(orderRequest.userid());
        String email;
        if(userRes.getStatusCode().is2xxSuccessful()){
            UserDto userDto = (UserDto) userRes.getBody();
            email = userDto.email();
            //Se podría verificar si el usuario puede realizar la compra o no
        }else{
            ApiResponse apiResponse = (ApiResponse) userRes.getBody();
            throw new ResourceNotFoundException(apiResponse.message());
        }

        //Verificar si los libros existen y traer su precio actual
        ResponseEntity<?> bookRes = bookFeign.getBooksPrice(orderRequest.items().toArray(new String[0]));
        List<BookDto> bookItems;
        if(bookRes.getStatusCode().is2xxSuccessful()) {
            bookItems = (List<BookDto>) bookRes.getBody();
        }else{
            ApiResponse apiResponse = (ApiResponse) bookRes.getBody();
            throw new ResourceNotFoundException(apiResponse.message());
        }

        //Verificar si los libros estan disponibles y si traer precio el correcto
        Set<OrderItem> orderItems = bookItems.stream().map(OrderMapper::toOrderItem).collect(Collectors.toSet());
        BigDecimal total = orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        OrderEntity order = OrderEntity.builder()
                .orderNum(UUID.randomUUID())
                .email(email)
                .userUUID(orderRequest.userid())
                .items(orderItems)
                .total(total)
                .status(OrderStatus.PENDING)
                .build();
        order = orderRepo.save(order);
        String message = String.format("Order with code %s and %s saved by user %s",
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
        try {
            ResponseEntity<?> response = payFeign.processOrderPayment(paymentRequest);
            if (response.getStatusCode().is2xxSuccessful()) {
                PaymentDto paymentDto = (PaymentDto) response.getBody();
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
            } else {
                ApiResponse apiResponse = (ApiResponse) response.getBody();
                throw new PaymentException(apiResponse.message());
            }
        } catch (FeignException e) {
            log.error("Error in feign client: {}", e.getMessage());
            throw new PaymentException("Error invoking payment service");
        }
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