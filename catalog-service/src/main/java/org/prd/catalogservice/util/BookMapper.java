package org.prd.catalogservice.util;

import org.prd.catalogservice.model.dto.BookDto;
import org.prd.catalogservice.model.entity.BookEntity;

public class BookMapper {

    public static BookDto toDto(BookEntity bookEntity) {
        return new BookDto(
                bookEntity.getName(),
                bookEntity.getDescription(),
                bookEntity.getCode(),
                bookEntity.getImgUri(),
                bookEntity.getPrice()
        );
    }

    public static BookEntity toEntity(BookDto bookDto) {
        return BookEntity.builder().
                name(bookDto.name()).
                description(bookDto.description()).
                code(bookDto.code()).
                imgUri(bookDto.imgUri()).
                price(bookDto.price()).
                build();

    }
}