package site.devroad.softeer.src.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
import site.devroad.softeer.src.user.dto.PostSignInReq;
import site.devroad.softeer.src.user.dto.PostSignInRes;
import site.devroad.softeer.src.user.dto.PostSignUpReq;
import site.devroad.softeer.src.user.dto.PostSignUpRes;
import site.devroad.softeer.utility.CustomRes;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/api/user/signup")
    public CustomRes<?> postSignUp(@RequestBody PostSignUpReq postSignUpReq) {
        if (postSignUpReq.hasNull())
            return new CustomException(ExceptionType.POST_ACCOUNT_FORM_INVALID).getResponseEntity();
        try {
            Long id = userService.join(postSignUpReq);
            return new CustomRes<>(new PostSignUpRes(id), HttpStatus.CREATED);
        } catch (CustomException e) {
            return e.getResponseEntity();
        }
    }


    @PostMapping("/api/user/signin")
    public CustomRes<?> postSignUp(@RequestBody PostSignInReq postSignInReq) {
        try {
            String jwt = userService.signIn(postSignInReq);
            return new CustomRes<>(new PostSignInRes(jwt), HttpStatus.OK);
        } catch (CustomException e) {
            return e.getResponseEntity();
        }
    }
}
