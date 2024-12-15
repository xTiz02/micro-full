package org.prd.catalogservice.service;

import org.prd.catalogservice.model.dto.BookDto;
import org.springframework.data.domain.Page;

public interface CatalogService {

    Page<BookDto> getBooks(int page, int size, String sort, String field);
    BookDto getBookByCode(String code);
}