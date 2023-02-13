package site.devroad.softeer.src.roadmap.dto.domain;

import site.devroad.softeer.src.exam.model.SubmissionType;

public class SubjectDetail {
    private String name;
    private Long subjectId;
    private SubmissionType mcqState;
    private SubmissionType frqState;
    private Long mcqExamId;
    private Long frqExamId;

    public SubjectDetail(String name, Long subjectId, SubmissionType mcqState, SubmissionType frqState, Long mcqExamId, Long frqExamId) {
        this.name = name;
        this.subjectId = subjectId;
        this.mcqState = mcqState;
        this.frqState = frqState;
        this.mcqExamId = mcqExamId;
        this.frqExamId = frqExamId;
    }

    public String getName() {
        return name;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public SubmissionType getMcqState() {
        return mcqState;
    }

    public SubmissionType getFrqState() {
        return frqState;
    }

    public Long getMcqExamId() {
        return mcqExamId;
    }

    public Long getFrqExamId() {
        return frqExamId;
    }
}
