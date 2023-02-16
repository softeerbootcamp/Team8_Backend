package site.devroad.softeer.src.exam.dto;

import site.devroad.softeer.src.exam.dto.domain.Assignment;

public class GetAssignmentDetail {
    private Boolean success;
    private Assignment assignment;

    public GetAssignmentDetail(Assignment assignment) {
        this.assignment = assignment;
        this.success = true;
    }

    public Boolean getSuccess() {
        return success;
    }

    public Assignment getAssignment() {
        return assignment;
    }
}
