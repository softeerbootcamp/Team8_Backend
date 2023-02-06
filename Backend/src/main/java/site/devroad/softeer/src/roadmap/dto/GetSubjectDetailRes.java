package site.devroad.softeer.src.roadmap.dto;

import site.devroad.softeer.src.course.model.Course;

import java.util.List;

public class GetSubjectDetailRes {
    private final List<Course> courses;
    private final boolean success;

    public GetSubjectDetailRes(List<Course> courses) {
        this.success = true;
        this.courses = courses;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public boolean isSuccess() {
        return success;
    }
}
