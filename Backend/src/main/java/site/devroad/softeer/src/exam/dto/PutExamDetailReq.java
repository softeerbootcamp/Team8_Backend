package site.devroad.softeer.src.exam.dto;

public class PutExamDetailReq {
    private final Long examId;
    private final Boolean result;

    public PutExamDetailReq(Long examId, Boolean result) {
        this.examId = examId;
        this.result = result;
    }

    public Long getExamId() {
        return examId;
    }

    public Boolean getResult() {
        return result;
    }
}
