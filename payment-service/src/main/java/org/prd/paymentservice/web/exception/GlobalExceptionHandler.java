package org.prd.paymentservice.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.prd.paymentservice.model.dto.ApiResponse;
import org.prd.paymentservice.model.dto.PaymentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //Controla los errores no esperados
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handlerException(Exception e,
                                                        WebRequest webRequest) {
        log.error(String.format("Error not expect in %s: %s",webRequest.getDescription(false), e.getMessage()));
        return new ResponseEntity<>(new ApiResponse("Error not expect",false), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}