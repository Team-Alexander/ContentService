package io.github.uptalent.content.exception.handler;

import io.github.uptalent.content.exception.ContentNotFoundException;
import io.github.uptalent.content.exception.IllegalContentModifyingException;
import io.github.uptalent.starter.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ContentExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ContentNotFoundException.class)
    public ErrorResponse handlerNotFoundException(ContentNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IllegalContentModifyingException.class)
    public ErrorResponse handlerConflictException(RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }
}
