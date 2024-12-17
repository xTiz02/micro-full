package org.prd.catalogservice.service;

import lombok.extern.slf4j.Slf4j;
import org.prd.catalogservice.model.dto.BookDto;
import org.prd.catalogservice.model.entity.BookEntity;
import org.prd.catalogservice.model.repository.CatalogRepository;
import org.prd.catalogservice.util.BookMapper;
import org.prd.catalogservice.web.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final CatalogRepository catRep;

    public BookServiceImpl(CatalogRepository catalogRepository) {
        this.catRep = catalogRepository;
    }


    @Override
    //@Transactional
    public void createBook(BookDto bookDto) {
        BookEntity bookEntity = BookMapper.toEntity(bookDto);
        bookEntity.setActive(true);
        catRep.save(bookEntity);
        log.info("Book with code {} saved", bookEntity.getCode());
    }

    @Override
    //@Transactional
    public void updateBook(String code, BookDto bookDto) {
        BookEntity bookEntity = catRep.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        bookEntity.setCode(bookDto.code());
        bookEntity.setDescription(bookDto.description());
        bookEntity.setImgUri(bookDto.imgUri());
        bookEntity.setName(bookDto.name());
        bookEntity.setPrice(bookDto.price());
        catRep.save(bookEntity);
        log.info("Book with code {} updated", bookEntity.getCode());
    }

    @Override
    //@Transactional
    public void deleteBook(String code) {
        BookEntity bookEntity = catRep.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        catRep.delete(bookEntity);
        log.info("Book with code {} deleted", bookEntity.getCode());
    }

    @Override
    //@Transactional
    public void changeStateBook(String code, boolean enable) {
        BookEntity bookEntity = catRep.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        catRep.updateActive(bookEntity.getCode(), enable);
        log.info("Book with code {} state changed to {}", bookEntity.getCode(), enable);
    }
}