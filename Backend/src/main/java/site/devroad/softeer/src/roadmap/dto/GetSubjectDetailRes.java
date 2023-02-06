package site.devroad.softeer.src.roadmap.dto;

import site.devroad.softeer.src.course.model.Course;

import java.util.ArrayList;
import java.util.List;

public class GetSubjectDetailRes {
    private final List<CourseDetail> courses;
    private final boolean success;

    static class CourseDetail {
        private Long id;
        private Long subjectId;
        private String courseName;
        private String tutorName;
        private String thumbnailUrl;
        private String explain;
        private Boolean finish;

        public CourseDetail(Long id, Long subjectId, String courseName, String tutorName, String thumbnailUrl, String explain) {
            this.id = id;
            this.subjectId = subjectId;
            this.courseName = courseName;
            this.tutorName = tutorName;
            this.thumbnailUrl = thumbnailUrl;
            this.explain = explain;
            this.finish = true;
        }
    }
    public GetSubjectDetailRes(List<Course> courses) {
        this.success = true;
        this.courses = new ArrayList<>();
    }

    public List<CourseDetail> getCourses() {
        return courses;
    }

    public boolean isSuccess() {
        return success;
    }
}
