package fr.formbuilder.exception;

import fr.formbuilder.models.ResponseData;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseData<Void>> handleValidationException(MethodArgumentNotValidException exception) {
        List<ResponseData.ErrorDetails> errors = exception.getBindingResult().getFieldErrors().stream()
            .map(e -> new ResponseData.ErrorDetails(e.getField(), e.getDefaultMessage()))
            .toList();

        ResponseData<Void> response = ResponseData.error(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InvalidFormDefinitionException.class)
    public ResponseEntity<ResponseData<Void>> handleInvalidFormDefinition(InvalidFormDefinitionException exception) {
        List<ResponseData.ErrorDetails> errors = List.of(new ResponseData.ErrorDetails(null, exception.getMessage()));
        ResponseData<Void> response = ResponseData.error(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Invalid form configuration", errors);
        return ResponseEntity.unprocessableEntity().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseData<Void>> handleConstraintViolation(ConstraintViolationException exception) {
        List<ResponseData.ErrorDetails> errors = exception.getConstraintViolations().stream()
                .map(e -> new ResponseData.ErrorDetails(null, e.getMessage()))
                .toList();
        ResponseData<Void> response = ResponseData.error(HttpStatus.BAD_REQUEST.value(), "Constraint Validation violated", errors);
        return ResponseEntity.badRequest().body(response);
    }
}

