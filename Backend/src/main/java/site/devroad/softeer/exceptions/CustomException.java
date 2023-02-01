package site.devroad.softeer.exceptions;

import java.util.Map;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import site.devroad.softeer.config.CorsUtility;

@Getter
public class CustomException extends Exception{
    private final ExceptionType exceptionType;

    public CustomException(ExceptionType response) {
        this.exceptionType = response;
    }

    public ExceptionType getResponse() {
        return exceptionType;
    }

    public ResponseEntity<?> getResponseEntity() {

        return new ResponseEntity<>(
                Map.of("message", exceptionType.getMessage(),
                        "customErrorCode", exceptionType.getCustomErrorCode()),
                CorsUtility.defaultHeader(),
                exceptionType.getStatus());
    }
}
