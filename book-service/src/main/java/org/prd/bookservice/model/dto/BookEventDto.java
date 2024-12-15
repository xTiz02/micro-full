package org.prd.bookservice.model.dto;

import org.prd.bookservice.util.EventType;

public record BookEventDto(
        String code,
        BookDto book,
        boolean enable,
        EventType eventType
) {
}