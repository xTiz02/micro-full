package org.prd.authservice.controller;

import org.prd.authservice.model.dto.ApiErrorDto;
import org.prd.authservice.util.error.AuthCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Controla los errores por credenciales o token incorrectos
    @ExceptionHandler(AuthCustomException.class)
    public ResponseEntity<ApiErrorDto> handleCustomException(AuthCustomException e, WebRequest webRequest){
        return new ResponseEntity<>(new ApiErrorDto(e.getMessage()), e.getStatus()==null? HttpStatus.UNAUTHORIZED:e.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDto> handlerException(Exception exception,
                                                        WebRequest webRequest) {
        ApiErrorDto apiResponse = new ApiErrorDto(exception.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}