package site.devroad.softeer.src.exam.model;

public class ExamSubmission {

    Long id;
    Long accountId;
    Long testId;
    String url;
    SubmissionType submissionType;
    String description;

    public ExamSubmission(Long id, Long accountId, Long testId, String url, SubmissionType submissionType, String description) {
        this.id = id;
        this.accountId = accountId;
        this.testId = testId;
        this.url = url;
        this.submissionType = submissionType;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long getTestId() {
        return testId;
    }

    public String getUrl() {
        return url;
    }

    public SubmissionType getSubmissionType() {
        return submissionType;
    }

    public String getDescription() {
        return description;
    }
}
