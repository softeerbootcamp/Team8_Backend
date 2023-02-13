package site.devroad.softeer.src.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
import site.devroad.softeer.src.user.dto.*;
import site.devroad.softeer.utility.JwtUtility;

@RestController
public class UserController {
    private final UserService userService;
    private final JwtUtility jwtUtility;

    public UserController(UserService userService, JwtUtility jwtUtility) {
        this.userService = userService;
        this.jwtUtility = jwtUtility;
    }

    @PostMapping("/api/user/signup")
    public ResponseEntity<?> postSignUp(@RequestBody PostSignUpReq postSignUpReq) {
        if (postSignUpReq.hasNull())
            return new CustomException(ExceptionType.POST_ACCOUNT_FORM_INVALID).getResponseEntity();
        try {
            Long id = userService.join(postSignUpReq);
            return new ResponseEntity<>(new PostSignUpRes(id), HttpStatus.CREATED);
        } catch (CustomException e) {
            return e.getResponseEntity();
        }
    }


    @PostMapping("/api/user/signin")
    public ResponseEntity<?> postSignIn(@RequestBody PostSignInReq postSignInReq) {
        try {
            Long accountId = userService.signIn(postSignInReq);
            String jwt = jwtUtility.makeJwtToken(accountId);
            if (userService.validateAdmin(accountId))
                return new ResponseEntity<>(new PostSignInRes(jwt, true), HttpStatus.OK);
            return new ResponseEntity<>(new PostSignInRes(jwt, false), HttpStatus.OK);
        } catch (CustomException e) {
            return e.getResponseEntity();
        }
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> getUserDetail(@RequestAttribute(value = "accountId") Long accountId) {
        try {
            GetUserDetailRes getUserDetailRes = userService.getUserDetail(accountId);
            return new ResponseEntity<>(getUserDetailRes, HttpStatus.OK);
        } catch (CustomException e) {
            return e.getResponseEntity();
        }
    }

    @GetMapping("/api/user/noRoadmap")
    public ResponseEntity<?> getNoRoadmapUser(@RequestAttribute(value = "accountId") Long accountId) {
        boolean isAdmin = userService.validateAdmin(accountId);
        if (isAdmin) {
            return new ResponseEntity<>(new GetNoUserRes(true, userService.getNoRoadmapUsers()), HttpStatus.OK);
        }
        return new CustomException(ExceptionType.NO_ADMIN_USER).getResponseEntity();
    }
}
