package site.devroad.softeer.src.roadmap.model;

public class Roadmap {
    private Long id;
    private String name;
    private Long chapterId;

    public Roadmap(Long id, String name, Long chapterId) {
        this.id = id;
        this.name = name;
        this.chapterId = chapterId;
    }
}
