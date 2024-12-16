package org.prd.orderservice.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class PaymentException extends RuntimeException {
    public PaymentException(String message) {
        super(message);
    }
}