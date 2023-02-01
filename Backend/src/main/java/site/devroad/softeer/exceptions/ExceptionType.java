package site.devroad.softeer.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonFormat
@Getter
public enum ExceptionType {
    //Account Related Exceptions
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "Account not found", 1001),
    ACCOUNT_ALREADY_EXISTS(HttpStatus.CONFLICT, "Account already exists", 1002),
    POST_ACCOUNT_FORM_INVALID(HttpStatus.BAD_REQUEST, "null 값이 회원가입 요청에 들어있습니다.", 1003),
    POST_ACCOUNT_EMAIL_DUPLICATED(HttpStatus.BAD_REQUEST, "중복된 email이  있습니다.", 1004),
    ACCOUNT_NOT_UPDATED(HttpStatus.BAD_REQUEST, "Account not updated", 1005),
    AUTHENTICATION_FAILED(HttpStatus.BAD_REQUEST, "Password가 잘못되었습니다.", 1006),
    POST_ACCOUNT_PHONE_DUPLICATED(HttpStatus.BAD_REQUEST, "중복된 휴대폰 번호가 있습니다.", 1007),


    //Database Related Exceptions
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Database error", 1101),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error", 1102);

    private HttpStatus httpStatus;
    private String message;
    private Integer customErrorCode;

    ExceptionType(HttpStatus httpStatus, String message, Integer customErrorCode) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.customErrorCode = customErrorCode;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }
}
