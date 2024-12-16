package org.prd.paymentservice.web.controller;

import jakarta.validation.Valid;
import org.prd.paymentservice.model.dto.PaymentDto;
import org.prd.paymentservice.model.dto.PaymentRequest;
import org.prd.paymentservice.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/status/hellCheck")
    public String hellCheck(){
        return "Hello from Payment Controller";
    }

    @PostMapping("/process")
    public ResponseEntity<PaymentDto> paymentOrder(@Valid @RequestBody PaymentRequest paymentRequest){
        return ResponseEntity.ok(paymentService.processPayment(paymentRequest));
    }
}