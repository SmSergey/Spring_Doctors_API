package com.app.web.advices;

import com.app.utils.messages.ErrorMessages;
import com.app.web.responses.ApiResponse;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorsAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationErrors(MethodArgumentNotValidException err) {
        val filedError = err.getFieldErrors().get(0);
        return new ApiResponse()
                .setMessage(filedError.getField() + " : " + filedError.getDefaultMessage())
                .setStatus(400)
                .build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleAuthErrors(BadCredentialsException err) {
        return new ApiResponse()
                .setMessage(err.getMessage())
                .setStatus(400)
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleCommonExceptions(Exception err) {
        err.printStackTrace();
        return new ApiResponse()
                .setMessage(ErrorMessages.INTERNAL_ERROR)
                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
    }
}
