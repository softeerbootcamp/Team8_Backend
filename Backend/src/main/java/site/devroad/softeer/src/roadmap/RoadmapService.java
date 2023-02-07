package site.devroad.softeer.src.roadmap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
import site.devroad.softeer.src.course.model.Subject;
import site.devroad.softeer.src.course.repository.SubjectRepo;
import site.devroad.softeer.src.roadmap.dto.PostRoadmapReq;
import site.devroad.softeer.src.roadmap.model.Roadmap;
import site.devroad.softeer.src.roadmap.model.SubjectToRoadmap;
import site.devroad.softeer.src.user.UserRepo;
import site.devroad.softeer.src.user.model.Account;
import site.devroad.softeer.src.user.model.LoginInfo;

import java.util.*;

@Service
public class RoadmapService {

    private static Logger logger = LoggerFactory.getLogger(RoadmapService.class);
    private final RoadmapRepo roadmapRepo;
    private final SubjectRepo subjectRepo;
    private final UserRepo userRepo;

    public RoadmapService(RoadmapRepo roadmapRepo, SubjectRepo subjectRepo, UserRepo userRepo) {
        this.roadmapRepo = roadmapRepo;
        this.subjectRepo = subjectRepo;
        this.userRepo = userRepo;
    }

    public Map<String, List<List<Object>>> getSubjects(Long accountId) throws CustomException {
        Long roadMapId = userRepo.findAccountById(accountId).getRoadMapId();
        Optional<Roadmap> roadmapById = roadmapRepo.findRoadmapById(roadMapId);
        if (roadmapById.isEmpty()) {
            throw new CustomException(ExceptionType.ROADMAP_NOT_FOUND);
        }
        Optional<List<SubjectToRoadmap>> strs = roadmapRepo.findSTRById(roadmapById.get().getId());
        if (strs.isEmpty()) {
            throw new CustomException(ExceptionType.SUBJECT_NOT_FOUND);
        }
        List<SubjectToRoadmap> subjectToRoadmaps = strs.get();
        Map<String, List<List<Object>>> subjects = new HashMap<>();
        for (SubjectToRoadmap str : subjectToRoadmaps) {
            Subject subjectById = subjectRepo.findById(str.getSubjectId()).orElseThrow();
            subjects.computeIfAbsent(str.getSequence().toString(), key -> new ArrayList<>())
                    .add(List.of(subjectById.getName(), subjectById.getId()));
        }
        return subjects;
    }

    public Long getCurChapterId(Long accountId) throws CustomException {
        Optional<Roadmap> roadmapByAccountId = roadmapRepo.findRoadmapByAccountId(accountId);
        if (roadmapByAccountId.isEmpty())
            throw new CustomException(ExceptionType.ROADMAP_NOT_FOUND);
        return roadmapByAccountId.get().getChapterId();
    }

    public void setCurChapterId(Long accountId, Long curChapterId) throws CustomException {
        Optional<Roadmap> roadmapByAccountId = roadmapRepo.findRoadmapByAccountId(accountId);
        if (roadmapByAccountId.isEmpty())
            throw new CustomException(ExceptionType.ROADMAP_NOT_FOUND);
        Long roadmapId = roadmapByAccountId.get().getId();
        roadmapRepo.updateCurChapterId(roadmapId, curChapterId);
    }

    public void createRoadmap(PostRoadmapReq roadmapReq) throws CustomException{
        Optional<LoginInfo> loginInfo = userRepo.findByEmail(roadmapReq.getEmail());
        if(loginInfo.isEmpty()){
            throw new CustomException(ExceptionType.ACCOUNT_NOT_FOUND);
        }
        Account account = userRepo.findAccountById(loginInfo.get().getAccountId());

        Long roadmapId = roadmapRepo.createRoadmap(account.getName() + "'s roadmap");

        userRepo.setRoadmap(account.getId(), roadmapId);

        for(int i = 0; i<roadmapReq.getSubjects().size(); i++) {
            roadmapRepo.addSubjectToRoadMap(roadmapId, roadmapReq.getSubjects().get(i), i+1);
        }
    }
}
