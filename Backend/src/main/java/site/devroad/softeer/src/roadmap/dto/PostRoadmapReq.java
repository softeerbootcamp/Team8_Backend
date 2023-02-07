package site.devroad.softeer.src.roadmap.dto;

import java.util.List;

public class PostRoadmapReq {
    private String email;
    private List<Long> subjectSequence;

    public PostRoadmapReq(String email, List<Long> subjects) {
        this.email = email;
        this.subjectSequence = subjects;
    }

    public String getEmail() {
        return email;
    }

    public List<Long> getSubjectSequence() {
        return subjectSequence;
    }
}
