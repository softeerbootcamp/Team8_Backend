package site.devroad.softeer.src.roadmap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.src.roadmap.RoadmapService;
import site.devroad.softeer.src.roadmap.subject.Subject;
import site.devroad.softeer.src.roadmap.course.CourseService;
import site.devroad.softeer.src.roadmap.subject.SubjectService;
import site.devroad.softeer.src.roadmap.dto.*;
import site.devroad.softeer.src.roadmap.dto.subdto.ChapterDetail;
import site.devroad.softeer.src.roadmap.dto.subdto.CourseDetail;

import java.util.List;
import java.util.Map;

@RestController
public class RoadmapController {

    private final RoadmapService roadmapService;
    private final SubjectService subjectService;
    private final CourseService courseService;

    public RoadmapController(RoadmapService roadmapService, SubjectService subjectService, CourseService courseService) {
        this.roadmapService = roadmapService;
        this.subjectService = subjectService;
        this.courseService = courseService;
    }

    @GetMapping("/api/roadmap")
    public ResponseEntity<?> getRoadmapSubjects(@RequestAttribute(value = "accountId") Long accountId) {
        Map<String, List<List<Object>>> subjects = roadmapService.getSubjects(accountId);
        return new ResponseEntity<>(new GetRoadmapDetailRes(subjects), HttpStatus.OK);
    }

    @GetMapping("/api/subject")
    public ResponseEntity<?> getAllSubjects() {
        List<Subject> allSubjects = subjectService.getAllSubjects();
        return new ResponseEntity<>(new GetAllSubjects(allSubjects), HttpStatus.OK);
    }

    @GetMapping("/api/subject/{subjectId}")
    public ResponseEntity<?> getSubjectDetail(@RequestAttribute(value = "accountId") Long accountId, @PathVariable("subjectId") String subjectId) {
        List<CourseDetail> courses = subjectService.getCourseDetails(Long.valueOf(subjectId), accountId);
        return new ResponseEntity<>(new GetSubjectDetailRes(courses), HttpStatus.OK);
    }

    @GetMapping("/api/course/{courseId}")
    public ResponseEntity<?> getCourseDetail(@RequestAttribute(value = "accountId") Long accountId, @PathVariable("courseId") String courseId) {
        List<ChapterDetail> chapterDetails = courseService.getChapterDetails(Long.valueOf(courseId));
        Long curChapterId = roadmapService.getCurChapterId(accountId);
        return new ResponseEntity<>(new GetCourseDetailRes(chapterDetails, curChapterId), HttpStatus.OK);
    }

    @PutMapping("/api/chapter/{chapterId}")
    public ResponseEntity<?> finishChapter(@RequestAttribute(value = "accountId") Long accountId, @PathVariable("chapterId") String chapterId) throws CustomException {
        Long chapterIdL = Long.valueOf(chapterId);
        roadmapService.setCurChapterId(accountId, chapterIdL);
        Long nextChapterId = courseService.getNextChapterId(chapterIdL);
        Boolean courseFinished = courseService.getCourseFinished(chapterIdL);
        return new ResponseEntity<>(new PutChapterFinishRes(courseFinished, nextChapterId), HttpStatus.ACCEPTED);
    }

    @PostMapping("/api/roadmap")
    public ResponseEntity<?> createRoadmap(@RequestBody PostRoadmapReq roadmapReq){
        roadmapService.createRoadmap(roadmapReq);
        return new ResponseEntity<>(new PostRoadmapRes(true), HttpStatus.CREATED);
    }

    @GetMapping("/api/chapter/{chapterId}")
    public ResponseEntity<?> getChapterDetail(@PathVariable("chapterId") String chapterId) {
        Long chapterIdL = Long.valueOf(chapterId);
        ChapterDetail chapterDetail = courseService.getChapterDetail(chapterIdL);
        return new ResponseEntity<>(new GetChapterDetailRes(chapterDetail), HttpStatus.OK);
    }
}
