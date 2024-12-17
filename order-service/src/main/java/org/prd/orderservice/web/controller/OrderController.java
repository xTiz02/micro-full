package org.prd.orderservice.web.controller;

import jakarta.validation.Valid;
import org.prd.orderservice.model.dto.ApiResponse;
import org.prd.orderservice.model.dto.OrderDto;
import org.prd.orderservice.model.dto.OrderRequest;
import org.prd.orderservice.service.OrderService;
import org.prd.orderservice.util.OrderStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/status/hellCheck")
    public String hellCheck(){
        return "Hello from Order Controller";
    }

    @GetMapping("/restrict/all")
    public ResponseEntity<?> findAllByPage(
            @RequestParam(required = true) UUID userId,
            @RequestParam(defaultValue = "0", required = true) int page,
            @RequestParam(defaultValue = "3", required = true) int size,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(required = false) String field)
    {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId,page, size, sort, field));
    }

    @GetMapping("/restrict/find")
    public ResponseEntity<OrderDto> getOrderByCode(@RequestParam(required = true) UUID orden){
        return ResponseEntity.ok(orderService.getOrderByNum(orden));
    }

    @GetMapping("/restrict/pay")
    public ResponseEntity<ApiResponse> paymentOrder(@RequestParam(required = true) UUID orderCode, @RequestParam(required = true) String payDataExample){
        return ResponseEntity.ok(orderService.payOrder(orderCode, payDataExample));
    }

    @PostMapping("/restrict/create")
    public ResponseEntity<ApiResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest){
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }

    @GetMapping("/restrict/cancel")
    public ResponseEntity<ApiResponse> cancelOrder(@RequestParam(name = "orderId") UUID orderId){
        return ResponseEntity.ok(orderService.changeStateOrder(orderId, OrderStatus.CANCELED));
    }
}