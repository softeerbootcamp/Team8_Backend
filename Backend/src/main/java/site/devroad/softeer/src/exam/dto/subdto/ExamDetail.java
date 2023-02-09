package site.devroad.softeer.src.exam.dto.subdto;

import site.devroad.softeer.src.exam.model.Exam;

public class ExamDetail {
    String url;
    String name;
    String explain;

    public ExamDetail(Exam exam){
        this.url = exam.getUrl();
        this.name = exam.getName();
        this.explain = exam.getDescription();
    }

    public ExamDetail(String url, String title, String explain) {
        this.url = url;
        this.name = title;
        this.explain = explain;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getExplain() {
        return explain;
    }
}
