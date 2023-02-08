package site.devroad.softeer.src.exam.model;

public class Exam {
    Long id;
    Long subjectId;
    String url;
    String name;
    String explain;
    Integer price;

    public Long getId() {
        return id;
    }

    public Long getSubjectId() {
        return subjectId;
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

    public Integer getPrice() {
        return price;
    }

    public Exam(Long id, Long subjectId, String url, String name, String explain, Integer price) {
        this.id = id;
        this.subjectId = subjectId;
        this.url = url;
        this.name = name;
        this.explain = explain;
        this.price = price;
    }
}
