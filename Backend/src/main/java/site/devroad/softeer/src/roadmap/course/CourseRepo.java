package site.devroad.softeer.src.roadmap.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import site.devroad.softeer.src.roadmap.course.Course;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseRepo(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Optional<Course> findCourseById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM Course WHERE id = ?"
                    , courseRowMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Course> findBySubjectId(Long subjectId) {
        try {
            return jdbcTemplate.query("SELECT * FROM Course WHERE subject_id = ?", courseRowMapper(), subjectId);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    private RowMapper<Course> courseRowMapper() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            Long subjectId = rs.getLong("subject_id");
            String tutorName = rs.getString("tutor_name");
            String thumbnailUrl = rs.getString("thumbnail_url");
            String description = rs.getString("description");
            Long languageId = rs.getLong("language_id");
            String type = rs.getString("type");
            String courseName = rs.getString("course_name");
            Course course = new Course();
            course.setId(id);
            course.setSubjectId(subjectId);
            course.setTutorName(tutorName);
            course.setThumbnailUrl(thumbnailUrl);
            course.setDescription(description);
            course.setLanguageId(languageId);
            course.setType(type);
            course.setCourseName(courseName);
            return course;
        };
    }
}
