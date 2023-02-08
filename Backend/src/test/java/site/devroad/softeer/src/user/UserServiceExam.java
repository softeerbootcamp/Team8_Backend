package site.devroad.softeer.src.user;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
import site.devroad.softeer.src.user.dto.PostSignInReq;
import site.devroad.softeer.src.user.dto.PostSignUpReq;
import site.devroad.softeer.src.user.model.Account;
import site.devroad.softeer.src.user.model.LoginInfo;
import site.devroad.softeer.utility.JwtUtility;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserServiceExam {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceExam.class);
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
        Optional<LoginInfo> optionalLoginInfo = userRepo.findByEmail(email);
        Optional<Account> optionalAccount = userRepo.findByPhone(phone);
        deleteUser(email, phone);
        assertThat(optionalLoginInfo).isPresent();
        assertThat(optionalAccount).isPresent();
    }

    @Test
    void duplicateEmailTest() {
        //given
        String dupEmail = "dupEmail@naver.com";
        String phone = "54321";
        createUser(dupEmail, "test", phone, "1234");
        PostSignUpReq postSignUpReq = new PostSignUpReq(dupEmail, "test1", "12345", "1234");
        //when
        try {
            userService.join(postSignUpReq);
        }
        //then
        catch (CustomException e) {
            assertThat(e.getExceptionType()).isEqualTo(ExceptionType.POST_ACCOUNT_EMAIL_DUPLICATED);
        } finally {
            deleteUser(dupEmail, phone);
        }
    }

    @Test
    void duplicatePhoneTest() {
        //given
        String dupPhone = "01000000000";
        String email = "test@gmail.com";
        createUser(email, "dupTest", dupPhone, "1234");
        PostSignUpReq postSignUpReq = new PostSignUpReq("hello@hanmail.net", "test1", dupPhone, "1234");

        //when
        try {
            userService.join(postSignUpReq);
        }
        //then
        catch (CustomException e) {
            assertThat(e.getExceptionType()).isEqualTo(ExceptionType.POST_ACCOUNT_PHONE_DUPLICATED);
        } finally {
            deleteUser(email, dupPhone);
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

    void createUser(String email, String name, String phone, String password) {
        PostSignUpReq req = new PostSignUpReq(email, name, phone, password);
        try {
            userService.join(req);
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

    void deleteUser(String email, String phone) {
        Optional<LoginInfo> optionalLoginInfo = userRepo.findByEmail(email);
        Optional<Account> optionalAccount = userRepo.findByPhone(phone);
        userRepo.deleteLoginInfoById(optionalLoginInfo.get().getId());
        userRepo.deleteAccountById(optionalAccount.get().getId());
    }
}
