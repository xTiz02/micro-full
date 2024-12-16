package org.prd.orderservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.prd.orderservice.util.PaymentStatus;

import java.util.UUID;

public record PaymentDto(
        @JsonProperty("order_id")
        UUID orderId,
        @JsonProperty("status")
        PaymentStatus paymentStatus,
        @JsonProperty("transaction_id")
        UUID transactionId
) {
}