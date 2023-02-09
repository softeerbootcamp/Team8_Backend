package site.devroad.softeer.src.exam.dto;

public class PostAssignSubmitReq {
    private String url;
    private String description;
    private Long examId;

    public PostAssignSubmitReq(String url, String description, Long examId) {
        this.url = url;
        this.description = description;
        this.examId = examId;
    }
    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public Long getExamId() {
        return examId;
    }

}
