package org.larma;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.larma.postnr.PostnrError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<PostnrError> handleException(
        final MethodArgumentNotValidException e)
    {
        return e.getFieldErrors().stream()
                    .map(v -> new PostnrError(v.getField(), v.getDefaultMessage()))
                    .collect(Collectors.toList());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<PostnrError> handleException(
        final ConstraintViolationException e)
    {
        return e.getConstraintViolations().stream()
                    .map(v -> new PostnrError(v.getPropertyPath().toString(), v.getMessage()))
                    .collect(Collectors.toList());
    }
}
