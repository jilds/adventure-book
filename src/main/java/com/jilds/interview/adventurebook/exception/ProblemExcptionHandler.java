package com.jilds.interview.adventurebook.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

/**
 * Handles exceptions according to RFC 7807.
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc7807">RFC 7807 - Problem Details for HTTP APIs</a>
 */
@Slf4j
@RestControllerAdvice
class ProblemExcptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected @Nullable ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return handleExceptionAsProblem(ex, headers, HttpStatus.valueOf(statusCode.value()), request);
    }

    @ExceptionHandler(value = ValidationException.class)
    protected ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
        return handleExceptionAsProblem(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionAsProblem(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionAsProblem(ex, headers, HttpStatus.BAD_REQUEST, request);
    }

    private ResponseEntity<Object> handleExceptionAsProblem(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        if (status.is4xxClientError()){
            return new ResponseEntity<>(ProblemFactory.clientError(ex, webRequest), headers, status);
        }

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            webRequest.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, RequestAttributes.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(ProblemFactory.unxpected(ex, webRequest), headers, status);
    }
}
