package site.devroad.softeer.src.roadmap.completedchapter;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;

import javax.sql.DataSource;
import java.sql.Date;

@Repository
public class CompletedChapterRepo {
    private final JdbcTemplate jdbcTemplate;

    public CompletedChapterRepo(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addCompletedChapter(Long accountId, Long chapterId) throws CustomException{
        try{
            jdbcTemplate.update("insert into CompletedChapter(account_id, chapter_id) values(?, ?, )", accountId, chapterId);
        }catch (DataAccessException e){
            throw new CustomException(ExceptionType.DATABASE_ERROR);
        }
    }

    private RowMapper<CompletedChapter> chapterRowMapper() {
        return (rs, rowNum) -> {
            long id = rs.getLong("id");
            long account_id = rs.getLong("account_id");
            long chapter_id = rs.getLong("chapter_id");
            Date created_at = rs.getDate("created_at");
            return new CompletedChapter(id, account_id, chapter_id, created_at);
        };
    }
}
