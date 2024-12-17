package org.prd.bookservice.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.prd.bookservice.model.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalHandlerException {

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

    //Controla los errores de validación de los campos de la url de la petición
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest webRequest){
        log.error(String.format("Method argument type mismatch in %s: %s",webRequest.getDescription(false), ex.getMessage()));
        String message = String.format("The parameter '%s' of value '%s' could not be converted to type %s",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
        return new ResponseEntity<>(new ApiResponse(message,false), HttpStatus.BAD_REQUEST);
    }


    //Controla los errores cuando no se encuentra un recurso
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceException(ResourceNotFoundException e, WebRequest webRequest){
        log.error(String.format("Resource not found in %s: %s",webRequest.getDescription(false), e.getMessage()));
        return new ResponseEntity<>(new ApiResponse(e.getMessage(),false), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(EventNotArriveException.class)
    public ResponseEntity<ApiResponse> handleEventException(ResourceNotFoundException e, WebRequest webRequest){
        log.error(String.format("Event not arrive in %s: %s",webRequest.getDescription(false), e.getMessage()));
        return new ResponseEntity<>(new ApiResponse(e.getMessage(),false), HttpStatus.INTERNAL_SERVER_ERROR);
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
        return new ResponseEntity<>(new ApiResponse("Error not expect",false), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}