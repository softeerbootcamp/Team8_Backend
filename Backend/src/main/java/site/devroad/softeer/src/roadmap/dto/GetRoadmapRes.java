package site.devroad.softeer.src.roadmap.dto;

import java.util.List;
import java.util.Map;

public class GetRoadmapRes {
    boolean success;
    Map<String, List<List<Object>>> subjects;

    public GetRoadmapRes(boolean success, Map<String, List<List<Object>>> subjects) {
        this.success = success;
        this.subjects = subjects;
    }

    public boolean isSuccess() {
        return success;
    }

    public Map<String, List<List<Object>>> getSubjects() {
        return subjects;
    }
}
