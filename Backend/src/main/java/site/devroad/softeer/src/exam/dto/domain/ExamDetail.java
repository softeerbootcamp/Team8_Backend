package site.devroad.softeer.src.exam.dto.domain;

import java.util.List;

public class ExamDetail {
    private String subjectName;
    private String url;
    private String name;
    private String description;
    private String type;

    private List<Integer> ans;
    private List<MultiChoiceQuestion> questions;

    private ExamDetail(String subjectName, String url, String name, String description, String type, List<Integer> ans, List<MultiChoiceQuestion> questions) {
        this.subjectName = subjectName;
        this.url = url;
        this.name = name;
        this.description = description;
        this.type = type;
        this.ans = ans;
        this.questions = questions;
    }

    public static ExamDetail createMCQDetail(ExamDetail examDetail, List<Integer> ans, List<MultiChoiceQuestion> questions) {
        return new ExamDetail(examDetail.subjectName, examDetail.url, examDetail.name,
                examDetail.description, examDetail.getType(), ans, questions);
    }

    public static ExamDetail createFRQDetail(String subjectName, String url, String name, String description, String type) {
        return new ExamDetail(subjectName, url, name, description, type, null, null);
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public List<Integer> getAns() {
        return ans;
    }

    public List<MultiChoiceQuestion> getQuestions() {
        return questions;
    }
}
