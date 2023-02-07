package site.devroad.softeer.src.roadmap.dto;

import site.devroad.softeer.src.course.model.Subject;

import java.util.List;

public class GetAllSubjects {
    private final boolean success;
    private final List<Subject> subjects;

    public GetAllSubjects(List<Subject> subjects) {
        this.success = true;
        this.subjects = subjects;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }
}
