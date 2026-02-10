package com.jilds.interview.adventurebook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import tools.jackson.core.exc.StreamReadException;
import tools.jackson.databind.exc.InvalidFormatException;

import java.net.URI;
import java.util.List;
import java.util.stream.Stream;

public class ProblemFactory {
    static Object clientError(Exception ex, WebRequest webRequest) {
        String requestURI = webRequest.getContextPath();

        if (webRequest instanceof ServletWebRequest servletWebRequest) {
            requestURI = servletWebRequest.getRequest().getRequestURI();
        }

        var problem = new Problem(URI.create("about:blank"), ex.getClass().getSimpleName(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), URI.create(requestURI), null);

        if (ex instanceof ValidationException validationException) {
            return new Problem(problem, validationException.getValidationErrors());
        }

        if (ex instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            return problemFromErrors(problem, methodArgumentNotValidException.getBindingResult());
        }

        if (ex instanceof HttpMessageNotReadableException messageNotReadableException) {
            if (messageNotReadableException.getCause() instanceof InvalidFormatException invalidFormatException) {
                var validationErrors = new ValidationError(invalidFormatException.getTargetType().getSimpleName(), invalidFormatException.getPath().getFirst().getPropertyName(), invalidFormatException.getValue(), null);
                return new Problem(problem, List.of(validationErrors));
            }

            if (messageNotReadableException.getCause() instanceof StreamReadException streamReadException) {
                var validationErrors = ValidationError.builder().message(streamReadException.getMessage()).build();
                return new Problem(problem, List.of(validationErrors));
            }
        }

        if (ex instanceof AdventureBookException adventureBookException) {
            var validationErrors = ValidationError.builder().message(adventureBookException.getMessage()).build();
            return new Problem(problem, List.of(validationErrors));
        }

        return problem;

    }

    static Problem adventureBook(AdventureBookException ex, WebRequest webRequest) {
        String requestURI = webRequest.getContextPath();

        if (webRequest instanceof ServletWebRequest servletWebRequest) {
            requestURI = servletWebRequest.getRequest().getRequestURI();
        }

        return new Problem(URI.create("about:blank"), ex.getClass().getSimpleName(), ex.getHttpStatus().value(), ex.getMessage(), URI.create(requestURI), null);
    }

    static Problem unxpected(Exception ex, WebRequest webRequest) {
        String requestURI = webRequest.getContextPath();

        if (webRequest instanceof ServletWebRequest servletWebRequest) {
            requestURI = servletWebRequest.getRequest().getRequestURI();
        }

        return new Problem(URI.create("about:blank"), ex.getClass().getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), URI.create(requestURI), null);
    }

    private static Problem problemFromErrors(Problem problem, Errors errors) {
        return new Problem(problem,
                Stream.concat(
                        errors.getFieldErrors().stream()
                                .map(error -> new ValidationError(
                                        error.getObjectName(),
                                        error.getField(),
                                        error.getRejectedValue(),
                                        error.getDefaultMessage())),
                        errors.getGlobalErrors().stream().map(error -> new ValidationError(error.getObjectName(), error.getDefaultMessage()))
                ).toList()
        );
    }
}
