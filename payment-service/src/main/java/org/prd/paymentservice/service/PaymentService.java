package org.prd.paymentservice.service;

import org.prd.paymentservice.model.dto.PaymentDto;
import org.prd.paymentservice.model.dto.PaymentRequest;

public interface PaymentService {
    PaymentDto processPayment(PaymentRequest paymentRequest);
}