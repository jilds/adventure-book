package com.jilds.interview.adventurebook.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.net.URI;
import java.util.List;


/**
 * A class representing a problem details response according to RFC 7807.
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc7807">RFC 7807 - Problem Details for HTTP APIs</a>
 */
@Getter
@RequiredArgsConstructor
public class Problem implements Serializable {
    private final URI type;
    private final String title;
    private final int status;
    private final String detail;
    private final URI instance;
    private final List<ValidationError> validationErrors;

    public Problem(Problem problem, String message, List<ValidationError> validationErrors) {
        validationErrors.add(ValidationError.builder().message(message).build());
        this(problem.getType(), problem.getTitle(), problem.getStatus(), problem.getDetail(), problem.getInstance(), validationErrors);
    }

    public Problem(Problem problem, List<ValidationError> validationErrors) {
        this(problem.getType(), problem.getTitle(), problem.getStatus(), problem.getDetail(), problem.getInstance(), validationErrors);
    }
}
