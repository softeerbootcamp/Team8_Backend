package site.devroad.softeer.src.course.service;

import org.springframework.stereotype.Service;
import site.devroad.softeer.src.course.model.Course;
import site.devroad.softeer.src.course.model.Subject;
import site.devroad.softeer.src.course.repository.CourseRepo;
import site.devroad.softeer.src.course.repository.SubjectRepo;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    private final SubjectRepo subjectRepo;
    private final CourseRepo courseRepo;

    public SubjectService(SubjectRepo subjectRepo, CourseRepo courseRepo) {
        this.subjectRepo = subjectRepo;
        this.courseRepo = courseRepo;
    }

    public List<Course> getCourses(Long subjectId) {
        return courseRepo.findBySubjectId(subjectId);
    }

    public Optional<Subject> getSubject(Long subjectId) {
        return subjectRepo.findById(subjectId);
    }
}
