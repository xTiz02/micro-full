package org.prd.bookservice.service;

import org.prd.bookservice.model.dto.ApiResponse;
import org.prd.bookservice.model.dto.BookDto;
import org.prd.bookservice.model.dto.BookItem;
import org.prd.bookservice.model.entity.BookEntity;
import org.prd.bookservice.model.repository.BookRepository;
import org.prd.bookservice.util.BookMapper;
import org.prd.bookservice.util.Util;
import org.prd.bookservice.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRep;
    private final BookEventService eventService;
    @Value("${spring-book-topic-name}")
    private String catalogTopic;

    public BookServiceImpl(BookRepository bookRepository, BookEventService eventService) {
        this.bookRep = bookRepository;
        this.eventService = eventService;
    }

    @Override
    @Transactional
    public ApiResponse saveBook(BookDto bookDto) {
        BookEntity bookEntity = BookMapper.toEntity(bookDto);
        bookEntity = bookRep.save(bookEntity);
        String message = String.format("Book with id %d saved", bookEntity.getId());

        eventService.sendEvent(catalogTopic, BookMapper.toCreateEventDto(bookEntity));

        return new ApiResponse(message, true);
    }

    @Override
    @Transactional
    public ApiResponse updateBook(Long id, BookDto bookDto) {
        BookEntity bookEntity = bookRep.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        String code = bookEntity.getCode();
        //bookEntity.setCode(bookDto.code());
        bookEntity.setDescription(bookDto.description());
        bookEntity.setImgUri(bookDto.imgUri());
        bookEntity.setName(bookDto.name());
        bookEntity.setPrice(bookDto.price());
        bookEntity.setUpdatedAt(LocalDateTime.now());
        bookEntity = bookRep.save(bookEntity);
        String message = String.format("Book with id %d updated", bookEntity.getId());

        eventService.sendEvent(catalogTopic, BookMapper.toUpdateEventDto(code, bookEntity));

        return new ApiResponse(message, true);

    }

    @Override
    @Transactional
    public ApiResponse deleteBook(Long id) {
        BookEntity bookEntity = bookRep.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        bookRep.deleteById(id);
        String message = String.format("Book with id %d deleted", id);

        eventService.sendEvent(catalogTopic, BookMapper.toDeleteEventDto(bookEntity.getCode()));

        return new ApiResponse(message, true);
    }

    @Override
    public BookEntity getBookById(Long id) {
        return bookRep.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    @Override
    public Page<BookEntity> getBooks(int page, int size, String sort, String field) {
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
    public BookEntity getBookByCode(String code) {
        return bookRep.findByCode(code).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

   @Override
    public boolean existsByCode(String code) {
        return bookRep.existsByCode(code);
    }
    @Override
    public boolean existsById(Long id) {
        return !bookRep.existsById(id);
    }

    @Override
    @Transactional
    public ApiResponse changeStateBook(Long id) {
        BookEntity bookEntity = bookRep.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        boolean isActive = bookEntity.isActive();
        bookRep.changeStatus(id,!isActive);

        eventService.sendEvent(catalogTopic, BookMapper.toEnableEventDto(bookEntity.getCode(),!isActive));

        return new ApiResponse("Book state changed as " + (!isActive ? "Enabled":"Disabled"), true);
    }

    @Override
    public List<BookItem> getBooksByCodes(String[] codes) {
        List<BookItem> bookItems = new ArrayList<>();
        for (String code : codes) {
            BookEntity bookEntity = bookRep.findByCode(code)
                    .orElseThrow(() -> new ResourceNotFoundException("Book with cod "+code+" not found"));
            bookItems.add(BookMapper.toItem(bookEntity));
        }
        return bookItems;
    }


}