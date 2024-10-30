package org.prd.authservice.model.dto;

import java.time.LocalDateTime;

public record ApiErrorDto(
        String message,
        LocalDateTime timestamp
) {
    public ApiErrorDto(String message) {
        this(message, LocalDateTime.now());
    }
}