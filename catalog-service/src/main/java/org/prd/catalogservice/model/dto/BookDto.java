package org.prd.catalogservice.model.dto;

import java.math.BigDecimal;

public record BookDto(
        String name,
        String description,
        String code,
        String imgUri,
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