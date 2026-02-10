package com.jilds.interview.adventurebook.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class AdventureBookException extends RuntimeException {

    @Getter
    private HttpStatus httpStatus;

    public AdventureBookException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
