package org.prd.orderservice.service;

import jakarta.validation.Valid;
import org.prd.orderservice.model.dto.PaymentDto;
import org.prd.orderservice.model.dto.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "micro-payment-service")
public interface PaymentFeignService {

    //Responde un PaymentDto o ApiResponse en caso de error
    @GetMapping("/payment/process")
    PaymentDto processOrderPayment(@Valid @RequestBody PaymentRequest paymentRequest);

}