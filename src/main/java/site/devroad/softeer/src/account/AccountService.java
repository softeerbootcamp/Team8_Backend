package site.devroad.softeer.src.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.src.account.DTO.PostAccountReq;
import site.devroad.softeer.src.account.DTO.PostAccountRes;

@Service
public class AccountService {

    @Autowired
    AccountRepo accountRepo;

    public PostAccountRes createAccount(PostAccountReq postAccountReq) throws CustomException {
        PostAccountRes postAccountRes = new PostAccountRes();
        postAccountRes.setResult("Success");
        return postAccountRes;
    }

    public Boolean isEmailDuplicated(String email) {
        return accountRepo.findByEmail(email).isPresent();
    }
}
