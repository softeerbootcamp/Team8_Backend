package site.devroad.softeer.src.test.model;

public class TestSubmission {

    Long id;
    Long accountId;
    Long testId;
    String url;
    SubmissionType submissionType;

    public TestSubmission(Long id, Long accountId, Long testId, String url, SubmissionType submissionType) {
        this.id = id;
        this.accountId = accountId;
        this.testId = testId;
        this.url = url;
        this.submissionType = submissionType;
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
}
