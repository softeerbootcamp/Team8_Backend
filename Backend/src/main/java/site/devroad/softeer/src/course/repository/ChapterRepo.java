package site.devroad.softeer.src.course.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import site.devroad.softeer.src.course.model.Chapter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class ChapterRepo {
    private final JdbcTemplate jdbcTemplate;

    public ChapterRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Chapter> findChapterById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM Chapter WHERE id = ?"
                    , chapterRowMapper(), id));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Chapter> findChapterByCourseId(Long courseId) {
        try {
            return jdbcTemplate.query("SELECT * FROM Chapter WHERE course_id = ?"
                    , chapterRowMapper(), courseId);
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    private RowMapper<Chapter> chapterRowMapper() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            Long courseId = rs.getLong("course_id");
            String title = rs.getString("title");
            String lectureUrl = rs.getString("lecture_url");
            String thumbnailUrl = rs.getString("thumbnail_url");
            String explain = rs.getString("explain");
            Integer sequence = rs.getInt("sequence");
            return new Chapter(id, courseId, title, lectureUrl, thumbnailUrl, explain, sequence);
        };
    }
}
