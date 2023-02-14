package site.devroad.softeer.src.roadmap.dto;

import site.devroad.softeer.src.roadmap.dto.domain.SubjectDetail;

import java.util.List;

public class GetRoadmapDetailRes {
    boolean success;
    List<SubjectDetail> subjects;

    public GetRoadmapDetailRes(List<SubjectDetail> subjects) {
        this.success = true;
        this.subjects = subjects;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<SubjectDetail> getSubjects() {
        return subjects;
    }
}
