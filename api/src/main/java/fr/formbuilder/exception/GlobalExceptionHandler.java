package fr.formbuilder.exception;

import fr.formbuilder.models.ResponseData;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseData<Void, List<ResponseData.ErrorDetails>>> handleValidationException(MethodArgumentNotValidException ex) {
        List<ResponseData.ErrorDetails> errors = ex.getBindingResult().getFieldErrors().stream()
            .map(e -> new ResponseData.ErrorDetails(e.getField(), e.getDefaultMessage()))
            .toList();

        ResponseData<Void, List<ResponseData.ErrorDetails>> response = ResponseData.error(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InvalidFormDefinitionException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidFormDefinition(InvalidFormDefinitionException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.UNPROCESSABLE_ENTITY.value());
        body.put("error", "Invalid form configuration");
        body.put("message", ex.getMessage());
        return ResponseEntity.unprocessableEntity().body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Constraint violation");
        body.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }
}

