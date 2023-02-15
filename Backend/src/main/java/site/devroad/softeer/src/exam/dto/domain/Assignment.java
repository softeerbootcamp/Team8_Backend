package site.devroad.softeer.src.exam.dto.domain;

import site.devroad.softeer.src.exam.model.ExamSubmission;
import site.devroad.softeer.src.exam.model.SubmissionType;

public class Assignment {
    private Long examId;
    private String description;
    private String url;
    private String username;
    private SubmissionType submissionType;

    public Assignment(ExamSubmission examSubmission, String username) {
        this.examId = examSubmission.getExamId();
        this.description = examSubmission.getDescription();
        this.url = examSubmission.getUrl();
        this.username = username;
        this.submissionType = examSubmission.getSubmissionType();
    }

    public Long getExamId() {
        return examId;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public SubmissionType getSubmissionType() {
        return submissionType;
    }
}
