package com.ruthless.lp.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

public class MyException extends ResponseStatusException {
    private final String message;

    public MyException(String message, HttpStatus httpStatus) {
        super(httpStatus);
        this.message = message;
    }
}
