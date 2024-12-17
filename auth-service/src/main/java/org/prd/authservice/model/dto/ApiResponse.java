package org.prd.authservice.model.dto;

public record ApiResponse(
        String message,
        boolean success
) {
}