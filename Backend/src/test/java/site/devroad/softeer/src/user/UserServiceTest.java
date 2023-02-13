package site.devroad.softeer.src.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
import site.devroad.softeer.src.user.dto.PostSignInReq;
import site.devroad.softeer.src.user.dto.PostSignUpReq;
import site.devroad.softeer.src.user.model.Account;
import site.devroad.softeer.src.user.model.LoginInfo;
import site.devroad.softeer.utility.JwtUtility;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class UserServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    JwtUtility jwtUtility;

    @Test
    void signupSuccessTest() {
        String email = "successTest@naver.com";
        String phone = "1234";
        try {
            PostSignUpReq postSignUpReq = new PostSignUpReq(email, "test1", phone, "1234");
            userService.join(postSignUpReq);
        } catch (CustomException e) {
            e.printStackTrace();
        }
        Optional<LoginInfo> optionalLoginInfo = userRepo.findLoginInfoByEmail(email);
        Optional<Account> optionalAccount = userRepo.findByPhone(phone);
        assertThat(optionalLoginInfo).isPresent();
        assertThat(optionalAccount).isPresent();
    }

    @Test
    void duplicateEmailTest() {
        //given
        String dupEmail = "dupdupEmail@naver.com";
        String phone = "01040402020";
        PostSignUpReq postSignUpReq1 = new PostSignUpReq(dupEmail, "test1", phone, "1234");
        PostSignUpReq postSignUpReq2 = new PostSignUpReq(dupEmail, "test1", "01020202020", "1234");
        //when
        try {
            userService.join(postSignUpReq1);
            userService.join(postSignUpReq2);
        }
        //then
        catch (CustomException e) {
            assertThat(e.getExceptionType()).isEqualTo(ExceptionType.POST_ACCOUNT_EMAIL_DUPLICATED);
        }
    }

    @Test
    void duplicatePhoneTest() {
        //given
        String dupPhone = "01000000000";
        String email = "test@gmail.com";

        PostSignUpReq postSignUpReq1 = new PostSignUpReq(email, "test1", dupPhone, "1234");
        PostSignUpReq postSignUpReq2 = new PostSignUpReq("hello@hanmail.net", "test2", dupPhone, "1234");

        //when
        try {
            userService.join(postSignUpReq1);
            userService.join(postSignUpReq2);
        }
        //then
        catch (CustomException e) {
            assertThat(e.getExceptionType()).isEqualTo(ExceptionType.POST_ACCOUNT_PHONE_DUPLICATED);
        }
    }

    @Test
    void signInTest() {
        //TODO : 회원가입 로직 및 회원 탈퇴 로직을 구현해서 테스트
        PostSignInReq postSignInReq = new PostSignInReq("1234@naver.com", "1234");
        try {
            Long userId = userService.signIn(postSignInReq);
            assertThat(userId).isInstanceOf(Long.class);
        } catch (CustomException e) {
            logger.warn(e.getMessage());
        }
    }

    @Test
    @DisplayName("사용자가 Exam을 올바르게 구매할 수 있는지 확인.")
    void examSubmissionTest(){
        try {
            //given
            Account account = userRepo.createAccountInfo("hi", "0100000092", "Student");
            userRepo.doSubscribe(account.getId());

            //when
            Boolean result = userRepo.isUserSubscribed(account.getId());

            //then
            assertTrue(result);
        }catch (CustomException e){
            logger.warn(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
