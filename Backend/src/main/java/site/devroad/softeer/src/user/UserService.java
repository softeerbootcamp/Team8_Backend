package site.devroad.softeer.src.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.devroad.softeer.src.user.dto.PostSignUpReq;
import site.devroad.softeer.src.user.model.Account;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    public boolean isEmailExist(String email) {
        return userRepo.findByEmail(email).isPresent();
    }

    public void join(PostSignUpReq req) {
        Account student = userRepo.addAccountInfo(req.getName(), req.getPhoneNumber(), "Student");
        userRepo.addLoginInfo(req.getEmail(), req.getPassword(), student.getId());
    }
}
