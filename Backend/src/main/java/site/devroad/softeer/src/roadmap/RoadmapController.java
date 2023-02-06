package site.devroad.softeer.src.roadmap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.src.course.model.Course;
import site.devroad.softeer.src.course.service.SubjectService;
import site.devroad.softeer.src.roadmap.dto.GetRoadmapDetailRes;
import site.devroad.softeer.src.roadmap.dto.GetSubjectDetailRes;
import site.devroad.softeer.utility.JwtUtility;

import java.util.List;
import java.util.Map;

@RestController
public class RoadmapController {

    private final RoadmapService roadmapService;
    private final SubjectService subjectService;

    public RoadmapController(RoadmapService roadmapService, SubjectService subjectService) {
        this.roadmapService = roadmapService;
        this.subjectService = subjectService;
    }

    @GetMapping("/api/roadmap")
    public ResponseEntity<?> getRoadmapSubjects(@RequestHeader(value = "jwt") String jwt) {
        try {
            JwtUtility.validateToken(jwt);
            Long accountId = JwtUtility.getAccountId(jwt);
            Map<String, List<List<Object>>> subjects = roadmapService.getSubjects(accountId);
            return new ResponseEntity<>(new GetRoadmapDetailRes(subjects), HttpStatus.OK);
        } catch (CustomException e) {
            return e.getResponseEntity();
        }
    }

    @GetMapping("/api/subject/{subjectId}")
    public ResponseEntity<?> getSubjectDetail(@RequestHeader(value = "jwt") String jwt, @PathVariable("subjectId") String subjectId) {
        try {
            JwtUtility.validateToken(jwt);
            List<Course> courses = subjectService.getCourses(Long.valueOf(subjectId));
            return new ResponseEntity<>(new GetSubjectDetailRes(courses), HttpStatus.OK);
        } catch (CustomException e) {
            return e.getResponseEntity();
        }
    }
}
