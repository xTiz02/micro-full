package org.prd.orderservice.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record OrderDto(
        @NotNull(message = "User id is required")
        UUID userid,
        @NotNull(message = "Items is required")
        Set<ItemDto> items,
        @NotNull(message = "Total is required") @DecimalMin(value = "0.1", message = "Total must be greater than 0.1")
        BigDecimal total
) {
}