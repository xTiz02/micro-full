package org.prd.bookservice.web.controller;

import jakarta.validation.Valid;
import org.prd.bookservice.model.dto.ApiResponse;
import org.prd.bookservice.model.dto.BookDto;
import org.prd.bookservice.model.dto.BookItem;
import org.prd.bookservice.model.entity.BookEntity;
import org.prd.bookservice.service.BookService;
import org.prd.bookservice.web.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/status/hellCheck")
    public ResponseEntity<String> hellCheck(){
        return ResponseEntity.ok("Hello from Book Controller");
    }

    @GetMapping("/restrict/all")
    public ResponseEntity<?> findAllByPage(
            @RequestParam(defaultValue = "0", required = true) int page,
            @RequestParam(defaultValue = "3", required = true) int size,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(required = false) String field)
    {
        return ResponseEntity.ok(bookService.getBooks(page, size, sort, field));
    }

    @PostMapping("/restrict/save")
    public ResponseEntity<ApiResponse> saveBook(@Valid @RequestBody BookDto book){
        return ResponseEntity.ok(bookService.saveBook(book));
    }

    @PutMapping("/restrict/update/{id}")
    public ResponseEntity<ApiResponse> updateBook(@PathVariable(name = "id") Long id,@Valid @RequestBody BookDto book){
        if(bookService.existsById(id) || bookService.existsByCode(book.code())){
            throw new ResourceNotFoundException("Book not found");
        }
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }

    @DeleteMapping("/restrict/delete/{id}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable(name = "id") Long id){
        if(bookService.existsById(id)){
            throw new ResourceNotFoundException("Book not found");
        }
        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    @GetMapping("/restrict/code/{code}")
    public ResponseEntity<BookEntity> getBookByCode(@PathVariable(name = "code") String code){
        return ResponseEntity.ok(bookService.getBookByCode(code));
    }

    @GetMapping("/restrict/id/{id}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/restrict/enable/{id}")
    public ResponseEntity<ApiResponse> enableBook(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(bookService.changeStateBook(id));
    }

    @GetMapping("/restrict/price")
    public ResponseEntity<List<BookItem>> getBooksByUuid(@RequestParam(name = "codes") String[] codes){
        if(codes.length == 0){
            throw new ResourceNotFoundException("Books not found");
        }
        return ResponseEntity.ok(bookService.getBooksByCodes(codes));
    }
}