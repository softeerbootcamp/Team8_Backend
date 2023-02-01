package site.devroad.softeer.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class CustomRes<T> extends ResponseEntity {

    private CustomRes(HttpStatus status) {
        super(status);
    }

    public CustomRes(T body, HttpStatus status) {
        super(body, CorsUtility.defaultHeader(), status);
    }

    private CustomRes(MultiValueMap headers, HttpStatus status) {
        super(headers, status);
    }

    private CustomRes(T body, MultiValueMap headers, HttpStatus status) {
        super(body, headers, status);
    }

    private CustomRes(Object body, MultiValueMap headers, int rawStatus) {
        super(body, headers, rawStatus);
    }
}
