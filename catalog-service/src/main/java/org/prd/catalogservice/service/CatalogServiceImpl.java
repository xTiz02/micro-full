package org.prd.catalogservice.service;

import org.prd.catalogservice.model.dto.BookDto;
import org.prd.catalogservice.model.repository.CatalogRepository;
import org.prd.catalogservice.util.BookMapper;
import org.prd.catalogservice.util.Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catRep;

    public CatalogServiceImpl(CatalogRepository catalogRepository) {
        this.catRep = catalogRepository;
    }

    @Override
    public Page<BookDto> getBooks(int page, int size, String sort, String field) {
        if(Util.isValidField(field) && Util.isValidSort(sort)){
            Sort directionSort = sort.equalsIgnoreCase("asc")?
                    Sort.by(field).ascending():
                    Sort.by(field).descending();
            Pageable pageable = PageRequest.of(page, size, directionSort);
            return catRep.findByActive(true,pageable).map(BookMapper::toDto);
        }else{
            Sort directionSort = Sort.by("id").ascending();
            return catRep.findByActive(
                    true,PageRequest.of(page, size, directionSort)).map(BookMapper::toDto);
        }
    }

    @Override
    public BookDto getBookByCode(String code) {
        return catRep.findByCode(code).map(BookMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }
}