package site.devroad.softeer.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.devroad.softeer.exceptions.CustomException;

@RestControllerAdvice
public class JwtExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleJwtValidationException(CustomException e) {
        return e.getResponseEntity();
    }
}
