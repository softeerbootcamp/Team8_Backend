package site.devroad.softeer.src.course.service;

import org.springframework.stereotype.Service;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
import site.devroad.softeer.src.course.model.Course;
import site.devroad.softeer.src.course.model.Subject;
import site.devroad.softeer.src.course.repository.CourseRepo;
import site.devroad.softeer.src.course.repository.SubjectRepo;
import site.devroad.softeer.src.roadmap.RoadmapRepo;
import site.devroad.softeer.src.roadmap.dto.subdto.CourseDetail;
import site.devroad.softeer.src.roadmap.model.Roadmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    private final SubjectRepo subjectRepo;
    private final CourseRepo courseRepo;
    private final RoadmapRepo roadmapRepo;

    public SubjectService(SubjectRepo subjectRepo, CourseRepo courseRepo, RoadmapRepo roadmapRepo) {
        this.subjectRepo = subjectRepo;
        this.courseRepo = courseRepo;
        this.roadmapRepo = roadmapRepo;
    }

    public List<CourseDetail> getCourseDetails(Long subjectId, Long accountId) throws CustomException {
        Optional<Roadmap> roadmapById = roadmapRepo.findRoadmapByAccountId(accountId);
        if (roadmapById.isEmpty()) {
            throw new CustomException(ExceptionType.ROADMAP_NOT_FOUND);
        }
        Long chapterId = roadmapById.get().getChapterId();
        List<Course> courses = courseRepo.findBySubjectId(subjectId);
        ArrayList<CourseDetail> courseDetails = new ArrayList<>();
        for (Course course : courses) {
            courseDetails.add(createCourseDetail(course));
        }
        return courseDetails;
    }

    public CourseDetail createCourseDetail(Course course) {
        return new CourseDetail(course);
    }

    public Optional<Subject> getSubject(Long subjectId) {
        return subjectRepo.findById(subjectId);
    }
}
