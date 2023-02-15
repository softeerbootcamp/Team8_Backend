package site.devroad.softeer.src.roadmap.dto;

import site.devroad.softeer.src.roadmap.dto.domain.CourseDetail;

import java.util.List;

public class GetSubjectDetailRes {
    private final List<CourseDetail> courses;
    private final boolean success;
    private final boolean finish;

    public GetSubjectDetailRes(List<CourseDetail> courses, boolean finish) {
        this.success = true;
        this.courses = courses;
        this.finish = finish;
    }

    public boolean isFinish() {
        return finish;
    }

    public List<CourseDetail> getCourses() {
        return courses;
    }

    public boolean isSuccess() {
        return success;
    }
}
