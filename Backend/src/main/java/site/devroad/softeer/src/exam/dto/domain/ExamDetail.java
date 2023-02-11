package site.devroad.softeer.src.exam.dto.domain;

public class ExamDetail {
    String subjectName;
    String url;
    String name;
    String description;

    public ExamDetail(String subjectName, String url, String title, String description) {
        this.subjectName = subjectName;
        this.url = url;
        this.name = title;
        this.description = description;
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
}
