package org.prd.bookservice.model.dto;

public record ApiResponse(
        String message,
        boolean success
) {
}