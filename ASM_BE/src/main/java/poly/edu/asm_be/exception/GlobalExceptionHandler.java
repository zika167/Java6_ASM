package poly.edu.asm_be.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import poly.edu.asm_be.dto.ApiResponse;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException ex, WebRequest request) {
        log.error("Runtime exception occurred: ", ex);
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(400, ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        log.error("Entity not found: ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(404, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        log.error("Validation failed: {}", errors);
        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(400, "Validation failed", errors));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Bad credentials: ", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(401, "Invalid username or password"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("Access denied: ", ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error(403, "Access denied"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Illegal argument: ", ex);
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(400, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex, WebRequest request) {
        log.error("Unexpected error occurred: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(500, "Internal server error"));
    }
}