package org.prd.bookservice.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class EventNotArriveException extends RuntimeException {
    public EventNotArriveException(String message) {
        super(message);
    }
}