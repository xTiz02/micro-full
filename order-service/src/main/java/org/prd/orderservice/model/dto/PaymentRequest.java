package org.prd.orderservice.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(
        @NotNull(message = "Order id is required")
        UUID orderId,
        @NotNull(message = "Amount is required") @DecimalMin(value = "0.1", message = "Amount must be greater than 0.1")
        BigDecimal amount,
        @NotNull(message = "User data is required")
        String userPayData
) {
}