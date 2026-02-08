package com.jilds.interview.adventurebook.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException{

    private List<ValidationError> validationErrors;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
        super(message);
    }
}
