package com.bfhl.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * GLOBAL EXCEPTION HANDLER
 * Returns clean JSON for every error — never HTML
 * is_success is always false here
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Validation errors — missing/invalid fields
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(false, "Validation failed: " + errors));
    }

    // Malformed JSON body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJson(
            HttpMessageNotReadableException ex) {

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(false, "Malformed JSON request body"));
    }

    // Catch-all for any other error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(false, "Server error: " + ex.getMessage()));
    }

    // Error response structure
    @Data
    @AllArgsConstructor
    public static class ErrorResponse {

        @JsonProperty("is_success")
        private boolean isSuccess;

        @JsonProperty("message")
        private String message;
    }
}
