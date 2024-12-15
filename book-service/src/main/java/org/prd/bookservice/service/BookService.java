package org.prd.bookservice.service;

import org.prd.bookservice.model.dto.ApiResponse;
import org.prd.bookservice.model.dto.BookDto;
import org.prd.bookservice.model.entity.Book;
import org.springframework.data.domain.Page;

public interface BookService {
    ApiResponse saveBook(BookDto bookDto);
    ApiResponse updateBook(Long id, BookDto bookDto);
    ApiResponse deleteBook(Long id);
    Book getBookById(Long id);
    Page<Book> getBooks(int page, int size,String sort, String field);
    Book getBookByCode(String code);
    boolean existsByCode(String code);
    boolean existsById(Long id);
}