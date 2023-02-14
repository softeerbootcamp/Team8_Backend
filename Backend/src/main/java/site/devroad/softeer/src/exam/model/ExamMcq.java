package site.devroad.softeer.src.exam.model;

import java.util.List;

public class ExamMcq {
    private final Long id;
    private final Long examId;
    private final Long sequence;
    private final String title;
    private final String content;
    private final List<String> choices;
    private final Integer ans;

    public ExamMcq(Long id, Long examId, Long sequence, String title, String content, List<String> choices, Integer ans) {
        this.id = id;
        this.examId = examId;
        this.sequence = sequence;
        this.title = title;
        this.content = content;
        this.choices = choices;
        this.ans = ans;
    }

    public Long getId() {
        return id;
    }

    public Long getExamId() {
        return examId;
    }

    public Long getSequence() {
        return sequence;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<String> getChoices() {
        return choices;
    }

    public Integer getAns() {
        return ans;
    }
}
