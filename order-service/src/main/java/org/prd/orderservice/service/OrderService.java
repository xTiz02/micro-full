package org.prd.orderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.prd.orderservice.model.dto.ApiResponse;
import org.prd.orderservice.model.dto.OrderDto;
import org.prd.orderservice.model.dto.OrderRequest;
import org.prd.orderservice.util.OrderStatus;
import org.springframework.data.domain.Page;
import java.util.UUID;

public interface OrderService {
    ApiResponse createOrder(OrderRequest orderRequest);
    ApiResponse payOrder(UUID orderNum, String payDataExample);
    ApiResponse changeStateOrder(UUID orderNum,OrderStatus orderStatus);
    Page<OrderDto> getOrdersByUser(UUID userId, int page, int size, String sort, String field);
    OrderDto getOrderByNum(UUID orderNum);
    //void changeStateOrder(UUID orderCode, OrderStatus orderStatus);
}