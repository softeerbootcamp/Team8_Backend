package site.devroad.softeer.src.roadmap.dto.subdto;

import site.devroad.softeer.src.course.model.Course;

public class CourseDetail {
    private Long id;
    private Long subjectId;
    private String courseName;
    private String tutorName;
    private String thumbnailUrl;
    private String explain;
    private Boolean finish;

    public CourseDetail(Course course) {
        this.id = course.getId();
        this.subjectId = course.getSubjectId();
        this.courseName = course.getCourseName();
        this.tutorName = course.getTutorName();
        this.thumbnailUrl = course.getThumbnailUrl();
        this.explain = course.getExplain();
        this.finish = true;
    }

    public Long getId() {
        return id;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getTutorName() {
        return tutorName;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getExplain() {
        return explain;
    }

    public Boolean getFinish() {
        return finish;
    }
}
