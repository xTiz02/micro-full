package org.prd.orderservice.service;

import jakarta.validation.Valid;
import org.prd.orderservice.model.dto.BookDto;
import org.prd.orderservice.model.dto.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "micro-book-service")
public interface BookFeignService {
    //Responde un List<BookDto> o ApiResponse en caso de error
    @GetMapping("/book/restrict/price")
    List<BookDto> getBooksPrice(@RequestParam(name = "codes") String[] codes);
}