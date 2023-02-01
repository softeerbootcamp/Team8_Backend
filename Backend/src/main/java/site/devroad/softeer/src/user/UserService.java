package site.devroad.softeer.src.user;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
import site.devroad.softeer.src.user.dto.PostSignInReq;
import site.devroad.softeer.src.user.dto.PostSignUpReq;
import site.devroad.softeer.src.user.model.Account;
import site.devroad.softeer.src.user.model.LoginInfo;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public boolean isEmailExist(String email) {
        return userRepo.findByEmail(email).isPresent();
    }

    public void join(PostSignUpReq req) {
        Account student = userRepo.addAccountInfo(req.getName(), req.getPhoneNumber(), "Student");
        String hashPassword = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt());
        userRepo.addLoginInfo(req.getEmail(), hashPassword, student.getId());
    }

    public String signIn(PostSignInReq req) throws CustomException {
        String email = req.getEmail();
        Optional<LoginInfo> loginInfo = userRepo.findByEmail(email);
        if (loginInfo.isEmpty()) {
            throw new CustomException(ExceptionType.ACCOUNT_NOT_FOUND);
        }
        String password = req.getPassword();
        boolean authentication = BCrypt.checkpw(password, loginInfo.get().getPassword());
        if (authentication) {
            return "magicalJWT";
        }
        throw new CustomException(ExceptionType.AUTHENTICATION_FAILED);
    }
}
