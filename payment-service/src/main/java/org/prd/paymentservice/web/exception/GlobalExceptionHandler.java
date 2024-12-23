package org.prd.paymentservice.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.prd.paymentservice.model.dto.ApiResponse;
import org.prd.paymentservice.model.dto.PaymentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    //Controla los errores de validación de los campos del cuerpo de la petición
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest webRequest){
        log.error(String.format("Method argument not valid in %s: %s",webRequest.getDescription(false), ex.getMessage()));
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .findFirst()
                .orElse(ex.getMessage());
        return new ResponseEntity<>(new ApiResponse(message,false), HttpStatus.BAD_REQUEST);
    }
    //controla cuando no se encuentra el path en el controlador
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse> handleNoHandlerFoundException(NoHandlerFoundException ex, WebRequest webRequest){
        String message = String.format("No handler found for %s %s", ex.getHttpMethod(), ex.getRequestURL());
        //404
        log.error(message);
        return new ResponseEntity<>(new ApiResponse(message,false), HttpStatus.NOT_FOUND);
    }

    //Controla los errores no esperados
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handlerException(Exception e,
                                                        WebRequest webRequest) {
        log.error(String.format("Error not expect in %s: %s",webRequest.getDescription(false), e.getMessage()));
        return new ResponseEntity<>(new ApiResponse("Error not expect from pyment service",false), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}