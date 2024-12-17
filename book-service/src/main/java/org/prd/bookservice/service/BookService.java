package org.prd.bookservice.service;

import org.prd.bookservice.model.dto.ApiResponse;
import org.prd.bookservice.model.dto.BookDto;
import org.prd.bookservice.model.dto.BookItem;
import org.prd.bookservice.model.entity.BookEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {
    ApiResponse saveBook(BookDto bookDto);
    ApiResponse updateBook(Long id, BookDto bookDto);
    ApiResponse deleteBook(Long id);
    BookEntity getBookById(Long id);
    Page<BookEntity> getBooks(int page, int size, String sort, String field);
    BookEntity getBookByCode(String code);
    boolean existsByCode(String code);
    boolean existsById(Long id);
    ApiResponse changeStateBook(Long id);

    List<BookItem> getBooksByCodes(String[] codes);
}