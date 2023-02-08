package site.devroad.softeer.utility;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;

import java.time.Duration;
import java.util.Date;

@Component
public class JwtUtility {
    private static Logger logger = LoggerFactory.getLogger(JwtUtility.class);

    //TODO : application.yml 에서 값을 불러오도록 수정할것.
    @Value("${jwt.secretKey}")
    private String secret;

    public String makeJwtToken(Long accountId) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)
                .setIssuer("fresh") // (2)
                .setIssuedAt(now) // (3)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(300).toMillis())) // (4)
                .claim("accountId", String.valueOf(accountId)) // (5)
                .signWith(SignatureAlgorithm.HS256, secret) // (6)
                .compact();
    }

    //throw custom Exception when error occurs
    public void validateToken(String jwt) throws CustomException {
        try {
            Jwts.parser().setSigningKey(secret).parse(jwt);
        } catch (Exception e) {
            logger.info("JWT error : {}", e.getMessage());
            throw new CustomException(ExceptionType.JWT_NOT_VALID);
        }
    }

    public Long getAccountId(String jwt) {
        return Long.parseLong((String) Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(jwt)
                .getBody().get("accountId"));
    }

}
