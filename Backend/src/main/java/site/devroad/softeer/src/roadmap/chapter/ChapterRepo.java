package site.devroad.softeer.src.roadmap.chapter;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import site.devroad.softeer.src.roadmap.chapter.Chapter;
import site.devroad.softeer.src.roadmap.dto.subdto.ChapterDetail;

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
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<ChapterDetail> findChapterDetailById(Long chapterId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT \n" +
                            "c.id as chapter_id,\n" +
                            "c.title as title,\n" +
                            "c.chapter_url as chapter_url,\n" +
                            "c.thumbnail_url as thumbnail_url,\n" +
                            "c.description as description,\n" +
                            "cc.id as completed\n" +
                            "FROM Chapter c \n" +
                            "LEFT JOIN CompletedChapter cc\n" +
                            "ON c.id = cc.chapter_id\n" +
                            "WHERE c.id = ?"
                    , chapterDetailRowMapper(), chapterId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Chapter> findChapterByCourseId(Long courseId) {
        try {
            return jdbcTemplate.query("SELECT * FROM Chapter WHERE course_id = ? ORDER BY sequence"
                    , chapterRowMapper(), courseId);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    public List<ChapterDetail> findChapterDetailByCourseId(Long courseId) {
        try {
            return jdbcTemplate.query("SELECT \n" +
                            "c.id as chapter_id,\n" +
                            "c.title as title,\n" +
                            "c.chapter_url as chapter_url,\n" +
                            "c.thumbnail_url as thumbnail_url,\n" +
                            "c.description as description,\n" +
                            "cc.id as completed\n" +
                            "FROM Chapter c \n" +
                            "LEFT JOIN CompletedChapter cc\n" +
                            "ON c.id = cc.chapter_id\n" +
                            "WHERE c.course_id = ?"
                    , chapterDetailRowMapper(), courseId);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    public Optional<Chapter> findNextChapter(Long courseId, Integer sequence) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM Chapter WHERE course_id = ? and sequence = ?"
                    , chapterRowMapper(), courseId, sequence));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Chapter> chapterRowMapper() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            Long courseId = rs.getLong("course_id");
            String title = rs.getString("title");
            String chapterUrl = rs.getString("chapter_url");
            String thumbnailUrl = rs.getString("thumbnail_url");
            String description = rs.getString("description");
            Integer sequence = rs.getInt("sequence");
            return new Chapter(id, courseId, title, chapterUrl, thumbnailUrl, description, sequence);
        };
    }

    private RowMapper<ChapterDetail> chapterDetailRowMapper() {
        return (rs, rowNum) -> {
            Long chapterId = rs.getLong("chapter_id");
            String title = rs.getString("title");
            String chapterUrl = rs.getString("chapter_url");
            String thumbnailUrl = rs.getString("thumbnail_url");
            String description = rs.getString("description");
            Long completed = rs.getLong("completed");
            boolean finish = !rs.wasNull();
            return new ChapterDetail(chapterId, title, chapterUrl, thumbnailUrl, description, finish);
        };
    }
}
