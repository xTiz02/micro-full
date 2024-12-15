package org.prd.catalogservice.service;

import org.prd.catalogservice.model.dto.BookDto;
import org.springframework.data.domain.Page;

public interface BookService {
    void createBook(BookDto bookDto);
    void updateBook(String code, BookDto bookDto);
    void deleteBook(String code);
    void changeStateBook(String code, boolean enable);
}