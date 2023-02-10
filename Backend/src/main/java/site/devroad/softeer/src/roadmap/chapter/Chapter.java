package site.devroad.softeer.src.roadmap.chapter;

public class Chapter {
    private final Long id;
    private final Long courseId;
    private final String title;
    private final String chapterUrl;
    private final String thumbnailUrl;
    private final String description;
    private final Integer sequence;

    public Chapter(Long id, Long courseId, String title, String chapterUrl, String thumbnailUrl, String description, Integer sequence) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.chapterUrl = chapterUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.sequence = sequence;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getChapterUrl() {
        return chapterUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getDescription() {
        return description;
    }

    public Integer getSequence() {
        return sequence;
    }
}
