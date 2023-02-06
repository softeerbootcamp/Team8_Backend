package site.devroad.softeer.src.roadmap.model;

public class Roadmap {
    private final Long id;
    private final String name;
    private final Long chapterId;

    public Roadmap(Long id, String name, Long chapterId) {
        this.id = id;
        this.name = name;
        this.chapterId = chapterId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getChapterId() {
        return chapterId;
    }
}
