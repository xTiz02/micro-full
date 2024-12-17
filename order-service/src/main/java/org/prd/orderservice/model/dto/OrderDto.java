package org.prd.orderservice.model.dto;

import org.prd.orderservice.util.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderDto(
        UUID orderNum,
        UUID userId,
        BigDecimal total,
        OrderStatus status,
        List<BookDto> items
) {
}