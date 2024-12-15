package org.prd.bookservice.util;

import org.prd.bookservice.model.dto.BookDto;
import org.prd.bookservice.model.entity.Book;

import java.time.LocalDateTime;

public class BookMapper {

    public static Book toEntity(BookDto bookDto) {
        return Book.builder()
                .code(bookDto.code())
                .name(bookDto.name())
                .description(bookDto.description())
                .price(bookDto.price())
                .imgUri(bookDto.imgUri() != null ?  bookDto.imgUri() : Util.defaultUri)
                .createdAt(LocalDateTime.now())
                .build();
    }
}