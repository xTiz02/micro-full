package org.prd.bookservice.util;

import org.prd.bookservice.model.dto.BookDto;
import org.prd.bookservice.model.dto.BookEventDto;
import org.prd.bookservice.model.entity.BookEntity;

import java.time.LocalDateTime;

public class BookMapper {

    public static BookEntity toEntity(BookDto bookDto) {
        return BookEntity.builder()
                .code(bookDto.code())
                .name(bookDto.name())
                .description(bookDto.description())
                .price(bookDto.price())
                .imgUri(bookDto.imgUri() != null ?  bookDto.imgUri() : Util.defaultUri)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static BookDto toDto(BookEntity bookEntity) {
        return new BookDto(
                bookEntity.getName(),
                bookEntity.getDescription(),
                bookEntity.getCode(),
                bookEntity.getImgUri(),
                bookEntity.getPrice()
        );
    }

    public static BookEventDto toCreateEventDto(BookEntity bookEntity) {
        return new BookEventDto(
                bookEntity.getCode(),
                toDto(bookEntity),
                bookEntity.isActive(),
                EventType.CREATE
        );
    }

    public static BookEventDto toUpdateEventDto(String code, BookEntity bookEntity) {
        return new BookEventDto(
                code,
                toDto(bookEntity),
                bookEntity.isActive(),
                EventType.UPDATE
        );
    }

    public static BookEventDto toDeleteEventDto(String code) {
        return new BookEventDto(
                code,
                null,
                false,
                EventType.DELETE
        );
    }

    public static BookEventDto toEnableEventDto(String code,boolean enable) {
        return new BookEventDto(
                code,
                null,
                enable,
                EventType.ENABLE
        );
    }
}