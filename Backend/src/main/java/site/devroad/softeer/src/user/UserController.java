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
    public ResponseEntity<?> postSignUp(@RequestBody PostSignInReq postSignInReq) {
        try {
            String jwt = jwtUtility.makeJwtToken(userService.signIn(postSignInReq));
            if (postSignInReq.getEmail().equals("admin@naver.com"))
                return new ResponseEntity<>(new PostSignInRes(jwt, true), HttpStatus.OK);
            return new ResponseEntity<>(new PostSignInRes(jwt, false), HttpStatus.OK);
        } catch (CustomException e) {
            return e.getResponseEntity();
        }
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> getMockUserDetail(@RequestHeader(value = "jwt") String jwt) {
        GetDetailRes getDetailRes = new GetDetailRes();
        getDetailRes.setUserId(1L);
        getDetailRes.setUserName("hello");
        getDetailRes.setCurChapterIdx(4L);
        getDetailRes.setTotalChapterIdx(0L);
        getDetailRes.setRoadmapId(1L);
        getDetailRes.setCurSubjectIdx(2L);
        getDetailRes.setTotalSubjectIdx(4L);
        return new ResponseEntity<>(getDetailRes, HttpStatus.OK);
    }

    @GetMapping("/api/user/real")
    public ResponseEntity<?> getUserDetail(@RequestHeader(value = "jwt") String jwt) {
        try {
            jwtUtility.validateToken(jwt);
            Long accountId = jwtUtility.getAccountId(jwt);
            GetDetailRes getDetailRes = new GetDetailRes();
            return new ResponseEntity<>(getDetailRes, HttpStatus.OK);
        } catch (CustomException e) {
            return e.getResponseEntity();
        }
    }

    @GetMapping("/api/user/noRoadmap")
    public ResponseEntity<?> getNoRoadmapUser(@RequestHeader(value = "jwt") String jwt) {
        try {
            jwtUtility.validateToken(jwt);
            Long accountId = jwtUtility.getAccountId(jwt);
            boolean isAdmin = userService.validateAdmin(accountId);
            if (isAdmin) {
                return new ResponseEntity<>(new GetNoUserRes(true, userService.getNoRoadmapUsers()), HttpStatus.OK);
            }
            return new CustomException(ExceptionType.NO_ADMIN_USER).getResponseEntity();
        } catch (CustomException e) {
            throw new RuntimeException(e);
        }
    }
}
