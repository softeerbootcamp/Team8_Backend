package site.devroad.softeer.src.course.model;

public class Chapter {
    Long id;
    Long courseId;
    String lectureUrl;
    String thumbnailUrl;
    String explain;
    Integer sequence;

    public Chapter(Long id, Long courseId, String lectureUrl, String thumbnailUrl, String explain, Integer sequence) {
        this.id = id;
        this.courseId = courseId;
        this.lectureUrl = lectureUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.explain = explain;
        this.sequence = sequence;
    }
}
