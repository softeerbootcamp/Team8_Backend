package site.devroad.softeer.src.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
import site.devroad.softeer.src.user.dto.PostSignUpReq;
import site.devroad.softeer.src.user.dto.PostSignUpRes;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/api/user/signup")
    public ResponseEntity<?> postSignUp(@RequestBody PostSignUpReq postSignUpReq) {
        if (postSignUpReq.hasNull())
            return new CustomException(ExceptionType.POST_ACCOUNT_FORM_INVALID).getResponseEntity();
        if (userService.isEmailExist(postSignUpReq.getEmail())) {
            return new CustomException(ExceptionType.POST_ACCOUNT_EMAIL_DUPLICATED).getResponseEntity();
        }
        userService.join(postSignUpReq);
        return new ResponseEntity<>(new PostSignUpRes(), HttpStatus.CREATED);
    }
}
