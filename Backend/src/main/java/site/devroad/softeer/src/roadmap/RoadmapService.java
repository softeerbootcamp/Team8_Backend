package site.devroad.softeer.src.roadmap;

import org.springframework.stereotype.Service;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
import site.devroad.softeer.src.course.CourseRepo;
import site.devroad.softeer.src.course.model.Subject;
import site.devroad.softeer.src.roadmap.model.SubjectToRoadmap;

import java.util.*;

@Service
public class RoadmapService {
    private final RoadmapRepo roadmapRepo;
    private final CourseRepo courseRepo;

    public RoadmapService(RoadmapRepo roadmapRepo, CourseRepo courseRepo) {
        this.roadmapRepo = roadmapRepo;
        this.courseRepo = courseRepo;
    }

    public Map<String, List<List<Object>>> getSubjects(String jwt, Long roadmapId) throws CustomException {
        //TODO: req의 JWT 검증
        Optional<List<SubjectToRoadmap>> strs = roadmapRepo.findSTRById(roadmapId);
        if (strs.isEmpty()) {
            throw new CustomException(ExceptionType.ROADMAP_NOT_FOUND);
        }
        List<SubjectToRoadmap> subjectToRoadmaps = strs.get();
        Map<String, List<List<Object>>> subjects = new HashMap<>();
        for (SubjectToRoadmap str : subjectToRoadmaps) {
            Subject subjectById = courseRepo.findSubjectById(str.getSubjectId())
                    .orElseThrow();
            subjects.computeIfAbsent(str.getSequence().toString(), key -> new ArrayList<>())
                    .add(List.of(subjectById.getName(), subjectById.getId()));
        }
        return subjects;
    }
}
