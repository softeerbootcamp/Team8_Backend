package site.devroad.softeer.src.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import site.devroad.softeer.src.exam.model.SubmissionType;
import site.devroad.softeer.src.exam.model.ExamSubmission;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class ExamSubmissionRepo {
    private static Logger logger = LoggerFactory.getLogger(ExamSubmission.class);

    JdbcTemplate jdbcTemplate;

    public ExamSubmissionRepo(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public Optional<ExamSubmission> findById(Long id){
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from ExamSubmission where id = ?",
                    examSubmissionRowMapper(), id));
        }catch
        (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public Optional<ExamSubmission> findByExamIdAndAccountId(Long examId, Long accountId) {
        try{
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "select * from ExamSubmission where exam_id = ? and account_id = ? order by id desc limit 1",
                examSubmissionRowMapper(),
                examId, accountId
        ));
        }
        catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public List<ExamSubmission> findByRoadmapIdAndAccountId(Long roadmapId, Long accountID) {
        return jdbcTemplate.query("SELECT es.*\n" +
                "FROM ExamSubmission es\n" +
                "JOIN Exam e ON es.exam_id = e.id\n" +
                "JOIN SubjectToRoadmap str ON e.subject_id = str.subject_id\n" +
                "WHERE str.roadmap_id = ?\n" +
                "AND es.account_id = ?", examSubmissionRowMapper(), roadmapId, accountID);
    }


    public RowMapper<ExamSubmission> examSubmissionRowMapper(){
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            Long accountId = rs.getLong("account_id");
            Long examId = rs.getLong("exam_id");
            String url = rs.getString("url");
            int pass_code = rs.getInt("is_passed");
            SubmissionType type = SubmissionType.getType(pass_code);
            String description = rs.getString("description");
            return new ExamSubmission(id, accountId, examId, url, type, description);
        };
    }
}
