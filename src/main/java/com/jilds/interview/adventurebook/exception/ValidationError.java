package com.jilds.interview.adventurebook.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;


/**
 * A class representing a problem details response according to RFC 7807.
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc7807">RFC 7807 - Problem Details for HTTP APIs</a>
 */
@Getter
@Builder
@RequiredArgsConstructor
public class ValidationError implements Serializable {
    private final String entity;
    private final String property;
    private final transient Object invalidValue;
    private final String message;

    public ValidationError(String entity, String message) {
        this(entity, null, null, message);
    }
}
