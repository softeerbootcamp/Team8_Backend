package site.devroad.softeer.src.roadmap.dto;

import java.util.List;

public class PostRoadmapReq {
    private String email;
    private List<Long> subjects;

    public PostRoadmapReq(String email, List<Long> subjects) {
        this.email = email;
        this.subjects = subjects;
    }

    public String getEmail() {
        return email;
    }

    public List<Long> getSubjects() {
        return subjects;
    }
}
