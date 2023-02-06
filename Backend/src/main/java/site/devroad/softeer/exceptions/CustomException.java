package site.devroad.softeer.exceptions;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public class CustomException extends Exception {
    private final ExceptionType exceptionType;

    public CustomException(ExceptionType response) {
        this.exceptionType = response;
    }

    public ExceptionType getResponse() {
        return exceptionType;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }

    public ResponseEntity<?> getResponseEntity() {
        return new ResponseEntity<>(
                Map.of("message", exceptionType.getMessage(),
                        "customErrorCode", exceptionType.getCustomErrorCode(),
                        "success", false),
                exceptionType.getStatus());
    }
}
