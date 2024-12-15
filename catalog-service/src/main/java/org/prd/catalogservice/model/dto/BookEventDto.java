package org.prd.catalogservice.model.dto;

import org.prd.catalogservice.util.EventType;

public record BookEventDto(
        String code,
        BookDto book,
        boolean enable,
        EventType eventType
) {
}