package site.devroad.softeer.src.user;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
import site.devroad.softeer.src.roadmap.RoadmapRepo;
import site.devroad.softeer.src.roadmap.course.CourseService;
import site.devroad.softeer.src.roadmap.model.Roadmap;
import site.devroad.softeer.src.user.dto.GetUserDetailRes;
import site.devroad.softeer.src.user.dto.PostSignInReq;
import site.devroad.softeer.src.user.dto.PostSignUpReq;
import site.devroad.softeer.src.user.model.Account;
import site.devroad.softeer.src.user.model.LoginInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final RoadmapRepo roadmapRepo;

    public UserService(UserRepo userRepo, RoadmapRepo roadmapRepo, CourseService courseService) {
        this.userRepo = userRepo;
        this.roadmapRepo = roadmapRepo;
    }

    public Long join(PostSignUpReq req) throws CustomException {
        validateSignUp(req);
        Account student = userRepo.createAccountInfo(req.getName(), req.getPhone(), "Student");
        String hashPassword = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt());
        return userRepo.createLoginInfo(req.getEmail(), hashPassword, student.getId()).getId();
    }

    public Long signIn(PostSignInReq req) throws CustomException {
        String email = req.getEmail();
        Optional<LoginInfo> loginInfo = userRepo.findByEmail(email);
        if (loginInfo.isEmpty()) {
            throw new CustomException(ExceptionType.ACCOUNT_NOT_FOUND);
        }
        String password = req.getPassword();
        boolean authentication = BCrypt.checkpw(password, loginInfo.get().getPassword());
        if (authentication) {
            return loginInfo.get().getAccountId();
        }
        throw new CustomException(ExceptionType.AUTHENTICATION_FAILED);
    }

    public void validateSignUp(PostSignUpReq req) throws CustomException {
        String phone = req.getPhone();
        String email = req.getEmail();
        if (userRepo.findByPhone(phone).isPresent())
            throw new CustomException(ExceptionType.POST_ACCOUNT_PHONE_DUPLICATED);
        if (userRepo.findByEmail(email).isPresent())
            throw new CustomException(ExceptionType.POST_ACCOUNT_EMAIL_DUPLICATED);
    }

    public boolean validateAdmin(Long accountId) throws CustomException {
        Account accountById = userRepo.findAccountById(accountId);
        return accountById.getType().equals("Admin");
    }

    public List<String> getNoRoadmapUsers() throws CustomException {
        List<LoginInfo> noRoadmapUser = userRepo.findNoRoadmapUser();
        List<String> users = new ArrayList<>();
        for (LoginInfo loginInfo : noRoadmapUser) {
            users.add(loginInfo.getEmail());
        }
        return users;
    }

    public GetUserDetailRes getUserDetail(Long accountId) throws CustomException {
        Account accountById = userRepo.findAccountById(accountId);
        Optional<Roadmap> roadmapByAccountId = roadmapRepo.findRoadmapByAccountId(accountId);
        GetUserDetailRes getUserDetailRes = new GetUserDetailRes();
        getUserDetailRes.setUserId(accountId);
        getUserDetailRes.setUserName(accountById.getName());
        getUserDetailRes.setRoadmapId(accountById.getRoadMapId());
        if (roadmapByAccountId.isPresent()) {
            Roadmap roadmap = roadmapByAccountId.get();
            getUserDetailRes.setCurChapterPK(roadmap.getChapterId());
        }
        return getUserDetailRes;
    }
}
