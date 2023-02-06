package site.devroad.softeer.src.roadmap;

import org.springframework.stereotype.Service;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
import site.devroad.softeer.src.course.model.Subject;
import site.devroad.softeer.src.course.repository.SubjectRepo;
import site.devroad.softeer.src.roadmap.model.Roadmap;
import site.devroad.softeer.src.roadmap.model.SubjectToRoadmap;
import site.devroad.softeer.src.user.UserRepo;

import java.util.*;

@Service
public class RoadmapService {
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
}
