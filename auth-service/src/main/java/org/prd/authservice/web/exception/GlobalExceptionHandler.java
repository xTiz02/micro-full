package org.prd.authservice.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.prd.authservice.model.dto.ApiErrorDto;
import org.prd.authservice.model.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //Controla los errores cuando no se encuentra un recurso
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceException(ResourceNotFoundException e, WebRequest webRequest){
        log.error(String.format("Resource not found in %s: %s",webRequest.getDescription(false), e.getMessage()));
        return new ResponseEntity<>(new ApiResponse(e.getMessage(),false), HttpStatus.NOT_FOUND);
    }

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