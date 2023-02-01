package site.devroad.softeer.src.user;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.src.user.dto.PostSignInReq;

@SpringBootTest
class UserServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
    @Autowired
    UserService userService;

    @Test
    void loginTest() {
        //TODO : 회원가입 로직 및 회원 탈퇴 로직을 구현해서 테스트 하십쇼 게으른 것들아ㅣ.
        PostSignInReq postSignInReq = new PostSignInReq("hello@naver.com", "1234");
        try {
            logger.info(userService.signIn(postSignInReq));
        } catch (CustomException e) {
            logger.warn(e.getMessage());
        }
    }
}
