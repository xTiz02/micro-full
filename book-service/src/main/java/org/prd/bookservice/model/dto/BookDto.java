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
        String imgUri,
        @DecimalMin(value = "0.1", message = "Price must be greater than 0")
        BigDecimal price
) {
        @Override
        public String toString() {
                return "BookDto{" +
                        "code='" + code + '\'' +
                        ", name='" + name + '\'' +
                        ", description='" + description + '\'' +
                        ", imgUri='" + imgUri + '\'' +
                        ", price=" + price +
                        '}';
        }
}