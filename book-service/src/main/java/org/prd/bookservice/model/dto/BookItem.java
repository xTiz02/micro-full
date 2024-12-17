package org.prd.bookservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public record BookItem(
        @JsonProperty("code")
        String code,
        @JsonProperty("name")
        String name,
        @JsonProperty("price")
        BigDecimal price
) {
}