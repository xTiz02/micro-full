package org.prd.orderservice.model.dto;

public record ApiResponse(
        String message,
        boolean success
) {
}