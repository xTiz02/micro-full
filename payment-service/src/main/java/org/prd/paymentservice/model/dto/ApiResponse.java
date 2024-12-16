package org.prd.paymentservice.model.dto;

public record ApiResponse(
        String message,
        boolean success
) {
}