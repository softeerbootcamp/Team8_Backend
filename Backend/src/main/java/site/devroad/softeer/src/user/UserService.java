package site.devroad.softeer.src.user;

import org.apache.catalina.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
import site.devroad.softeer.src.exam.ExamSubmissionRepo;
import site.devroad.softeer.src.exam.model.ExamSubmission;
import site.devroad.softeer.src.exam.model.SubmissionType;
import site.devroad.softeer.src.roadmap.RoadmapRepo;
import site.devroad.softeer.src.roadmap.chapter.ChapterRepo;
import site.devroad.softeer.src.roadmap.completedchapter.CompletedChapterRepo;
import site.devroad.softeer.src.roadmap.course.CourseRepo;
import site.devroad.softeer.src.roadmap.model.Roadmap;
import site.devroad.softeer.src.roadmap.subject.SubjectRepo;
import site.devroad.softeer.src.user.dto.GetAllUserRes;
import site.devroad.softeer.src.user.dto.GetUserDetailRes;
import site.devroad.softeer.src.user.dto.PostSignInReq;
import site.devroad.softeer.src.user.dto.PostSignUpReq;
import site.devroad.softeer.src.user.dto.domain.UserDetail;
import site.devroad.softeer.src.user.model.Account;
import site.devroad.softeer.src.user.model.LoginInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final RoadmapRepo roadmapRepo;
    private final SubjectRepo subjectRepo;
    private final ExamSubmissionRepo examSubmissionRepo;
    private final CompletedChapterRepo completedChapterRepo;
    private final CourseRepo courseRepo;
    private final ChapterRepo chapterRepo;

    public UserService(UserRepo userRepo, RoadmapRepo roadmapRepo, SubjectRepo subjectRepo, ExamSubmissionRepo examSubmissionRepo, CompletedChapterRepo completedChapterRepo, CourseRepo courseRepo, ChapterRepo chapterRepo) {
        this.userRepo = userRepo;
        this.roadmapRepo = roadmapRepo;
        this.subjectRepo = subjectRepo;
        this.examSubmissionRepo = examSubmissionRepo;
        this.completedChapterRepo = completedChapterRepo;
        this.courseRepo = courseRepo;
        this.chapterRepo = chapterRepo;
    }

    public Long join(PostSignUpReq req){
        validateSignUp(req);
        Account student = userRepo.createAccountInfo(req.getName(), req.getPhone(), "Student");
        String hashPassword = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt());
        return userRepo.createLoginInfo(req.getEmail(), hashPassword, student.getId()).getId();
    }

    public Long signIn(PostSignInReq req){
        String email = req.getEmail();
        Optional<LoginInfo> loginInfo = userRepo.findLoginInfoByEmail(email);
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

    public void validateSignUp(PostSignUpReq req){
        String phone = req.getPhone();
        String email = req.getEmail();
        if (userRepo.findByPhone(phone).isPresent())
            throw new CustomException(ExceptionType.POST_ACCOUNT_PHONE_DUPLICATED);
        if (userRepo.findLoginInfoByEmail(email).isPresent())
            throw new CustomException(ExceptionType.POST_ACCOUNT_EMAIL_DUPLICATED);
    }

    public boolean validateAdmin(Long accountId){
        Account accountById = userRepo.findAccountById(accountId);
        return accountById.getType().equals("Admin");
    }

    public List<String> getNoRoadmapUsers(){
        List<LoginInfo> noRoadmapUser = userRepo.findNoRoadmapUser();
        List<String> users = new ArrayList<>();
        for (LoginInfo loginInfo : noRoadmapUser) {
            users.add(loginInfo.getEmail());
        }
        return users;
    }

    public Boolean isUserSubscribe(Long accountId){
        return userRepo.isUserSubscribed(accountId);
    }

    public GetUserDetailRes getUserDetail(Long accountId) throws CustomException {
        Account accountById = userRepo.findAccountById(accountId);
        String userName = accountById.getName();
        Optional<Roadmap> roadmapByAccountId = roadmapRepo.findRoadmapByAccountId(accountId);
        //roadmapId가 없다면 subscribe 여부는 false
        if (roadmapByAccountId.isEmpty())
            return GetUserDetailRes.createNoRoadmapUserDetail(accountId, userName, false);
        GetUserDetailRes getUserDetailRes = GetUserDetailRes.createUserDetail();
        getUserDetailRes.setUserId(accountId);
        getUserDetailRes.setUserName(userName);
        getUserDetailRes.setRoadmapId(accountById.getRoadMapId());
        Roadmap roadmap = roadmapByAccountId.get();
        Long chapterId = roadmap.getChapterId();
        Long roadmapId = roadmap.getId();
        getUserDetailRes.setCurChapterPK(chapterId);
        int totalSubjects = subjectRepo.findSubjectsByRoadmapId(roadmapId).size();
        getUserDetailRes.setTotalSubjectIdx((long) totalSubjects);
        List<ExamSubmission> byRoadmapIdAndAccountId = examSubmissionRepo.findByRoadmapIdAndAccountId(roadmapId, accountId);
        long cnt = byRoadmapIdAndAccountId.stream()
                .filter(examSubmission -> examSubmission.getSubmissionType() == SubmissionType.PASSED)
                .count();
        getUserDetailRes.setCurSubjectIdx(cnt + 1);
        //진행 중인 챕터가 없을 때
        if (chapterId == -1) {
            getUserDetailRes.setChapterPercent(1F);
            return getUserDetailRes;
        }
        Long courseId = chapterRepo.findChapterById(chapterId).get().getCourseId();
        int totalChapterCnt = chapterRepo.findChaptersByCourseId(courseId).size();
        int completedChapterCnt = completedChapterRepo.readCompletedChapters(accountId, courseId).size();
        getUserDetailRes.setChapterPercent(completedChapterCnt / (float) totalChapterCnt);
        return getUserDetailRes;
    }

    public List<UserDetail> getAllUser(Long accountId){
        List<UserDetail> allUser = userRepo.findAllUser();
        return allUser;
    }
}
