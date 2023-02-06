package site.devroad.softeer.src.test.model;

public class Test {
    Long id;
    Long course_id;
    String url;
    String name;
    String explain;
    Integer price;

    public Long getId() {
        return id;
    }

    public Long getCourse_id() {
        return course_id;
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

    public Test(Long id, Long course_id, String url, String name, String explain, Integer price) {
        this.id = id;
        this.course_id = course_id;
        this.url = url;
        this.name = name;
        this.explain = explain;
        this.price = price;
    }
}
