package site.devroad.softeer.utility;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.devroad.softeer.exceptions.CustomException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class JwtUtilityTest {
    @Autowired
    JwtUtility jwtUtility;

    @Test
    void makeJwtToken() {
        //given
        String jwt = jwtUtility.makeJwtToken(13L, "hello");
        //when
        try {
            jwtUtility.validateToken(jwt);
            Long id = jwtUtility.getAccountId(jwt);

            //then
            assertThat(id == 13L).isTrue();

        } catch (CustomException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
