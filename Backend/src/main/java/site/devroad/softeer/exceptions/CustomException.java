package site.devroad.softeer.exceptions;

import site.devroad.softeer.utility.CustomRes;

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

    public CustomRes<?> getResponseEntity() {
        return new CustomRes<>(
                Map.of("message", exceptionType.getMessage(),
                        "customErrorCode", exceptionType.getCustomErrorCode(),
                        "success", false),
                exceptionType.getStatus());
    }
}
