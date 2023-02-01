package site.devroad.softeer.src.course.model;

public class Course {
    Long id;
    Long subjectId;
    String tutorName;
    String explain;
    Long languageId;
    String type;

    public Course(Long id, Long subjectId, String tutorName, String explain, Long languageId, String type) {
        this.id = id;
        this.subjectId = subjectId;
        this.tutorName = tutorName;
        this.explain = explain;
        this.languageId = languageId;
        this.type = type;
    }
}
