package site.devroad.softeer.src.roadmap.completedchapter;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.List;

@Repository
public class CompletedChapterRepo {
    private final JdbcTemplate jdbcTemplate;

    public CompletedChapterRepo(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createCompletedChapter(Long accountId, Long chapterId) throws CustomException {
        try {
            jdbcTemplate.update("insert into CompletedChapter(account_id, chapter_id) values(?, ?)", accountId, chapterId);
        } catch (DataAccessException e) {
            throw new CustomException(ExceptionType.DATABASE_ERROR);
        }
    }

    public List<CompletedChapter> readCompletedChapters(Long accountId, Long courseId) {
        return jdbcTemplate.query("SELECT cc.*\n" +
                "FROM CompletedChapter cc\n" +
                "JOIN Chapter c ON cc.chapter_id = c.id\n" +
                "WHERE c.course_id = ?\n" +
                "AND cc.account_id = ?;\n", completedChapterRowMapper(), courseId, accountId);
    }

    private RowMapper<CompletedChapter> completedChapterRowMapper() {
        return (rs, rowNum) -> {
            long id = rs.getLong("id");
            long account_id = rs.getLong("account_id");
            long chapter_id = rs.getLong("chapter_id");
            Date created_at = rs.getDate("created_at");
            return new CompletedChapter(id, account_id, chapter_id, created_at);
        };
    }
}
