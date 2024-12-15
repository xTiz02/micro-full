package org.prd.bookservice.service;

import org.prd.bookservice.model.dto.ApiResponse;
import org.prd.bookservice.model.dto.BookDto;
import org.prd.bookservice.model.entity.Book;
import org.prd.bookservice.model.repository.BookRepository;
import org.prd.bookservice.util.BookMapper;
import org.prd.bookservice.util.Util;
import org.prd.bookservice.web.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRep;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRep = bookRepository;
    }

    @Override
    public ApiResponse saveBook(BookDto bookDto) {
        Book book = BookMapper.toEntity(bookDto);
        book = bookRep.save(book);
        String message = String.format("Book with id %d saved", book.getId());
        return new ApiResponse(message, true);
    }

    @Override
    public ApiResponse updateBook(Long id, BookDto bookDto) {
        Book book = bookRep.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        book.setCode(bookDto.code());
        book.setDescription(bookDto.description());
        book.setImgUri(bookDto.imgUri());
        book.setName(bookDto.name());
        book.setPrice(bookDto.price());
        book.setUpdatedAt(LocalDateTime.now());
        book = bookRep.save(book);
        String message = String.format("Book with id %d updated", book.getId());
        return new ApiResponse(message, true);

    }

    @Override
    public ApiResponse deleteBook(Long id) {
        bookRep.deleteById(id);
        String message = String.format("Book with id %d deleted", id);
        return new ApiResponse(message, true);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRep.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    @Override
    public Page<Book> getBooks(int page, int size,String sort, String field) {
        if(Util.isValidField(field) && Util.isValidSort(sort)){
            Sort directionSort = sort.equalsIgnoreCase("asc")?
                    Sort.by(field).ascending():
                    Sort.by(field).descending();
            Pageable pageable = PageRequest.of(page, size, directionSort);
            return bookRep.findAll(pageable);
        }else{
            Sort directionSort = Sort.by("id").ascending();
            return bookRep.findAll(PageRequest.of(page, size, directionSort));
        }
    }

    @Override
    public Book getBookByCode(String code) {
        return bookRep.findByCode(code).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

   @Override
    public boolean existsByCode(String code) {
        return bookRep.existsByCode(code);
    }
    @Override
    public boolean existsById(Long id) {
        return bookRep.existsById(id);
    }


}