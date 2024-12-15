package org.prd.bookservice.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BookDto(
        @NotNull(message = "Name is required")
        String name,
        String description,
        @NotNull(message = "Code is required")
        String code,
        @NotNull(message = "Image URI is required")
        String imgUri,
        @DecimalMin(value = "0.1", message = "Price must be greater than 0")
        BigDecimal price
) {
}