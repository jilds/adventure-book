package com.jilds.interview.adventurebook.exception;

import lombok.Builder;

import java.io.Serializable;


/**
 * A class representing a problem details response according to RFC 7807.
 *
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc7807">RFC 7807 - Problem Details for HTTP APIs</a>
 */
@Builder
public record ValidationError(String entity, String property, Object invalidValue, String message) implements Serializable {

    public ValidationError(String entity, String message) {
        this(entity, null, null, message);
    }

}
