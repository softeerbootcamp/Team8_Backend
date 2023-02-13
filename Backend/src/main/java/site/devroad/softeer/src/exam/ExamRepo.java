package site.devroad.softeer.src.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
import site.devroad.softeer.src.exam.dto.domain.ExamDetail;
import site.devroad.softeer.src.exam.model.Exam;
import site.devroad.softeer.src.exam.model.ExamSubmission;
import site.devroad.softeer.src.exam.model.SubmissionType;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class ExamRepo {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public ExamRepo(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public Optional<Exam> findExamBySubjectId(Long subjectId){
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from Exam where subject_id = ?", examRowMapper(), subjectId));
        }
        catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public Optional<Exam> findExamById(Long examId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from Exam where id = ?", examRowMapper(), examId));
        }
        catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public Optional<ExamDetail> findExamDetailById(Long examId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT \n" +
                            "s.name AS subject_name,\n" +
                            "e.url AS url, \n" +
                            "e.name AS name,\n" +
                            "e.description AS description\n" +
                            "FROM Subject s\n" +
                            "JOIN Exam e \n" +
                            "ON s.id = e.subject_id\n" +
                            "WHERE e.id = ?",
                    examDetailRowMapper(), examId));
        }
        catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public void delete(Long examId){
        jdbcTemplate.update("delete * from Exam where id = ?", examId);
    }


    public void subscribeExam(Long accountId, Long examId) throws CustomException {
        jdbcTemplate.update("insert into PurchasedExam(account_id, exam_id) values(?, ?)", accountId, examId);
    }



    public void addExamSubmission(Long accountId, Long examId, String url, String description) throws CustomException {
            jdbcTemplate.update("insert into ExamSubmission(account_id, exam_id, url, is_passed, description) " +
                    "values(?, ?, ?, 3, ?)", accountId, examId, url, description);

    }
    RowMapper<ExamSubmission> examSubmissionRowMapper(){
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            Long accountId = rs.getLong("account_id");
            Long examId = rs.getLong("test_id");
            String url = rs.getString("url");
            SubmissionType submissionType = SubmissionType.getType(rs.getInt("is_passed"));
            String explain = rs.getString("description");
            return new ExamSubmission(id, accountId, examId, url, submissionType, explain);
        };
    }
    RowMapper<Exam> examRowMapper(){
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            Long subject_id = rs.getLong("subject_id");
            String url = rs.getString("url");
            String name = rs.getString("name");
            String description = rs.getString("description");
            Integer price = rs.getInt("price");
            return new Exam(id, subject_id, url, name, description, price);
        };
    }

    RowMapper<ExamDetail> examDetailRowMapper(){
        return (rs, rowNum) -> {
            String subjectName = rs.getString("subject_name");
            String url = rs.getString("url");
            String name = rs.getString("name");
            String description = rs.getString("description");
            return new ExamDetail(subjectName, url, name, description);
        };
    }
}
