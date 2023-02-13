package site.devroad.softeer.src.roadmap.dto.domain;

public class SubjectDetail {
    private String name;
    private Long subjectId;
    private String mcqState;
    private String frqState;
    private String mcqExamId;
    private String frqExamId;

    public SubjectDetail(String name, Long subjectId, String mcqState, String frqState, String mcqExamId, String frqExamId) {
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

    public String getMcqState() {
        return mcqState;
    }

    public String getFrqState() {
        return frqState;
    }

    public String getMcqExamId() {
        return mcqExamId;
    }

    public String getFrqExamId() {
        return frqExamId;
    }
}
