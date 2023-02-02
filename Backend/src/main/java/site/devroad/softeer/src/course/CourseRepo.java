package site.devroad.softeer.src.course;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import site.devroad.softeer.src.course.model.Chapter;
import site.devroad.softeer.src.course.model.Course;
import site.devroad.softeer.src.course.model.Subject;

@Repository
public class CourseRepo {

    private RowMapper<Course> courseRowMapper(){
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            Long subjectId = rs.getLong("subject_id");
            String tutorName = rs.getString("tutor_name");
            String thumbnailUrl = rs.getString("thumbnail_url");
            String explain = rs.getString("explain");
            Long languageId = rs.getLong("language_id");
            String type = rs.getString("type");
            return new Course(id, subjectId, tutorName, thumbnailUrl, explain, languageId, type);
        };
    }

    private RowMapper<Chapter> chapterRowMapper(){
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            Long courseId = rs.getLong("course_id");
            String lectureUrl = rs.getString("lecture_url");
            String thumbnailUrl = rs.getString("thumbnail_url");
            String explain = rs.getString("explain");
            Integer sequence = rs.getInt("sequence");
            return new Chapter(id, courseId, lectureUrl, thumbnailUrl, explain, sequence);
        };
    }
    private RowMapper<Subject> subjectRowMapper(){
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            String explain = rs.getString("explain");
            return new Subject(id, name, explain);
        };
    }
}
