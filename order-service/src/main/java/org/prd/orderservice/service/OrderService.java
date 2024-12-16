package org.prd.orderservice.service;

import org.prd.orderservice.model.dto.ApiResponse;
import org.prd.orderservice.model.dto.OrderDto;
import org.prd.orderservice.util.OrderStatus;
import org.springframework.data.domain.Page;
import java.util.UUID;

public interface OrderService {
    ApiResponse createOrder(OrderDto orderDto);
    ApiResponse payOrder(UUID orderCode, String payDataExample);
    ApiResponse changeStateOrder(UUID orderCode,OrderStatus orderStatus);
    Page<OrderDto> getOrdersByUser(UUID userId,int page, int size, String sort, String field);
    OrderDto getOrderByCode(UUID orderCode);
    //void changeStateOrder(UUID orderCode, OrderStatus orderStatus);
}