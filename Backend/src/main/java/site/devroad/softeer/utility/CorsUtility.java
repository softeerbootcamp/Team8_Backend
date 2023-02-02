package site.devroad.softeer.utility;

import org.springframework.http.HttpHeaders;

public class CorsUtility {

    public static HttpHeaders defaultHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        headers.add("Access-Control-Max-Age", "3600");
        headers.add("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token");
        headers.add("Access-Control-Expose-Headers", "xsrf-token");
        return headers;
    }
}
