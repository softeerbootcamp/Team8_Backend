package site.devroad.softeer.utility;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import site.devroad.softeer.exceptions.CustomException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class JwtUtilityTest {

    @Test
    void makeJwtToken() {
        //given
        String jwt = JwtUtility.makeJwtToken(13L);
        //when
        try {
            JwtUtility.validateToken(jwt);
            Long id = JwtUtility.getAccountId(jwt);

            //then
            assertThat(id == 13L).isTrue();

        } catch (CustomException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}