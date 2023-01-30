package site.devroad.softeer.src.account;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.src.account.DTO.PostAccountReq;
import site.devroad.softeer.src.account.DTO.PostAccountRes;
import site.devroad.softeer.exceptions.ExceptionType;

@RestController
public class AccountController {
    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;

    @GetMapping("/account")
    public ResponseEntity<?> getAccount() {
        return new ResponseEntity<>(Map.of("Success", "true"), null, 200);
    }

    @PostMapping(value = "/account", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> postAccount(@RequestBody PostAccountReq postAccountReq){
        // validation check - not null, not empty, some format check
        // validation check - is id duplicated
        // do register

        if(!postAccountReq.isValid()){
            return new CustomException(ExceptionType.POST_ACCOUNT_FORM_INVALID).getResponseEntity();
        }

        if(!accountService.isEmailDuplicated(postAccountReq.getEmail())){
            return new CustomException(ExceptionType.POST_ACCOUNT_EMAIL_DUPLICATED).getResponseEntity();
        }

        try {
            PostAccountRes postAccountRes = accountService.createAccount(postAccountReq);
            logger.info("postAccountReq: " + postAccountReq.toString());
            return new ResponseEntity<>(postAccountRes, null, HttpStatus.CREATED);
        }
        catch (CustomException e){
            return e.getResponseEntity();
        }
    }

    @Getter @Setter @AllArgsConstructor
    class ttt{
        private String name;
        private String email;
    }

}
