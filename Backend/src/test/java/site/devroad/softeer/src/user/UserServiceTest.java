package site.devroad.softeer.src.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.src.user.dto.PostSignInReq;
import site.devroad.softeer.src.user.dto.PostSignUpReq;

@SpringBootTest
class UserServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepo;

    @Test
    void signupTest() {
        PostSignUpReq postSignUpReq = new PostSignUpReq("1234@naver.com", "test1", "01023456789", "1234");
        userService.join(postSignUpReq);
        Assertions.assertThat(userRepo.findByEmail("1234@naver.com")).isPresent();
    }


    @Test
    void signInTest() {
        //TODO : 회원가입 로직 및 회원 탈퇴 로직을 구현해서 테스트
        PostSignInReq postSignInReq = new PostSignInReq("1234@naver.com", "1234");
        try {
            logger.info(userService.signIn(postSignInReq));
        } catch (CustomException e) {
            logger.warn(e.getMessage());
        }
    }
}
