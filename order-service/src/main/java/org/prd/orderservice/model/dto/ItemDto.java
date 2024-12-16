package org.prd.orderservice.model.dto;

import java.math.BigDecimal;

public record ItemDto(
        String code,
        String name,
        BigDecimal price
) {
}