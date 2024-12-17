package org.prd.orderservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public record BookDto(
        @JsonProperty("code")
        String code,
        @JsonProperty("name")
        String name,
        @JsonProperty("price")
        BigDecimal price
) {
}