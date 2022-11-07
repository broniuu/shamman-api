package com.example.toikprojekt2022.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UserAlreadyExistException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExistsException(UserAlreadyExistException exception)
    {
        ApiError error = new ApiError();
        error.setException(": " + exception.getMessage());
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<ApiError>(error, status);
    }
}
