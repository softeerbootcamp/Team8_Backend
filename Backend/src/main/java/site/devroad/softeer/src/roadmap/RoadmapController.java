package site.devroad.softeer.src.roadmap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.src.course.model.Subject;
import site.devroad.softeer.src.course.service.CourseService;
import site.devroad.softeer.src.course.service.SubjectService;
import site.devroad.softeer.src.roadmap.dto.*;
import site.devroad.softeer.src.roadmap.dto.subdto.ChapterDetail;
import site.devroad.softeer.src.roadmap.dto.subdto.CourseDetail;
import site.devroad.softeer.utility.JwtUtility;

import java.util.List;
import java.util.Map;

@RestController
public class RoadmapController {

    private final RoadmapService roadmapService;
    private final SubjectService subjectService;
    private final CourseService courseService;
    private final JwtUtility jwtUtility;

    public RoadmapController(RoadmapService roadmapService, SubjectService subjectService, CourseService courseService, JwtUtility jwtUtility) {
        this.roadmapService = roadmapService;
        this.subjectService = subjectService;
        this.courseService = courseService;
        this.jwtUtility = jwtUtility;
    }

    @GetMapping("/api/roadmap")
    public ResponseEntity<?> getRoadmapSubjects(@RequestHeader(value = "jwt") String jwt) {
        try {
            jwtUtility.validateToken(jwt);
            Long accountId = jwtUtility.getAccountId(jwt);
            Map<String, List<List<Object>>> subjects = roadmapService.getSubjects(accountId);
            return new ResponseEntity<>(new GetRoadmapDetailRes(subjects), HttpStatus.OK);
        } catch (CustomException e) {
            return e.getResponseEntity();
        }
    }

    @GetMapping("/api/subject")
    public ResponseEntity<?> getAllSubjects() {
        List<Subject> allSubjects = subjectService.getAllSubjects();
        return new ResponseEntity<>(new GetAllSubjects(allSubjects), HttpStatus.OK);
    }

    @GetMapping("/api/subject/{subjectId}")
    public ResponseEntity<?> getSubjectDetail(@RequestHeader(value = "jwt") String jwt, @PathVariable("subjectId") String subjectId) {
        try {
            jwtUtility.validateToken(jwt);
            Long accountId = jwtUtility.getAccountId(jwt);
            List<CourseDetail> courses = subjectService.getCourseDetails(Long.valueOf(subjectId), accountId);
            return new ResponseEntity<>(new GetSubjectDetailRes(courses), HttpStatus.OK);
        } catch (CustomException e) {
            return e.getResponseEntity();
        }
    }

    @GetMapping("/api/course/{courseId}")
    public ResponseEntity<?> getCourseDetail(@RequestHeader(value = "jwt") String jwt, @PathVariable("courseId") String courseId) {
        try {
            jwtUtility.validateToken(jwt);
            Long accountId = jwtUtility.getAccountId(jwt);
            List<ChapterDetail> chapterDetails = courseService.getChapterDetails(Long.valueOf(courseId));
            Long curChapterId = roadmapService.getCurChapterId(accountId);
            return new ResponseEntity<>(new GetCourseDetailRes(chapterDetails, curChapterId), HttpStatus.OK);
        } catch (CustomException e) {
            return e.getResponseEntity();
        }
    }

    @PutMapping("/api/chapter/{chapterId}")
    public ResponseEntity<?> finishChapter(@RequestHeader(value = "jwt") String jwt, @PathVariable("chapterId") String chapterId) {
        try {
            jwtUtility.validateToken(jwt);
            Long accountId = jwtUtility.getAccountId(jwt);
            Long chapterIdL = Long.valueOf(chapterId);
            roadmapService.setCurChapterId(accountId, chapterIdL);
            Long nextChapterId = courseService.getNextChapterId(chapterIdL);
            Boolean courseFinished = courseService.getCourseFinished(chapterIdL);
            return new ResponseEntity<>(new PutChapterFinishRes(courseFinished, nextChapterId), HttpStatus.ACCEPTED);
        } catch (CustomException e) {
            return e.getResponseEntity();
        }
    }
}
