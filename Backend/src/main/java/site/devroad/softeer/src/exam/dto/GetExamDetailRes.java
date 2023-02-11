package site.devroad.softeer.src.exam.dto;

import site.devroad.softeer.src.exam.dto.domain.ExamDetail;

public class GetExamDetailRes {
    Boolean success;

    ExamDetail examDetail;


    public GetExamDetailRes(Boolean success, ExamDetail examDetail) {
        this.success = success;
        this.examDetail = examDetail;
    }

    public Boolean getSuccess() {
        return success;
    }

    public ExamDetail getExamDetail() {
        return examDetail;
    }
}
