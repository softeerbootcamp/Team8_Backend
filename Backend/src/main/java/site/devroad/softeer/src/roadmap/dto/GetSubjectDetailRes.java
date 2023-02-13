package site.devroad.softeer.src.roadmap.dto;

import site.devroad.softeer.src.roadmap.dto.domain.CourseDetail;

import java.util.List;

public class GetSubjectDetailRes {
    private final List<CourseDetail> courses;
    private final boolean success;

    public GetSubjectDetailRes(List<CourseDetail> courses) {
        this.success = true;
        this.courses = courses;
    }

    public List<CourseDetail> getCourses() {
        return courses;
    }

    public boolean isSuccess() {
        return success;
    }
}
