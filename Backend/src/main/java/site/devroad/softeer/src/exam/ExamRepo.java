package site.devroad.softeer.src.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
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
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from Test where subject_id = ?", examRowMapper(), subjectId));
        }
        catch(DataAccessException e){
            return Optional.empty();
        }
    }

    public Optional<Exam> findExamById(Long examId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from Test where id = ?", examRowMapper(), examId));
        }
        catch(DataAccessException e){
            return Optional.empty();
        }
    }

    public void delete(Long testId){
        jdbcTemplate.update("delete * from Test where id = ?", testId);
    }


    public void addExamToAccount(Long accountId, Long examId) throws CustomException {
        try{
            jdbcTemplate.update("insert into PurchasedTest(account_id, test_id) values(?, ?)", accountId, examId);
        }catch(DataAccessException e){
            throw new CustomException(ExceptionType.DATABASE_ERROR);
        }
    }

    public Boolean isExamPurchased(Long accountId, Long examId) throws CustomException{
        try{
            Long result  = jdbcTemplate.queryForObject(
                    "select count(*) from PurchasedTest where account_id = ? and test_id = ?", Long.class, accountId, examId);
            return result == 1L;
        }catch(DataAccessException e){
            throw new CustomException(ExceptionType.DATABASE_ERROR);
        }
    }


    public void addExamSubmission(Long accountId, Long examId, String url, String description) throws CustomException {
        try {
            jdbcTemplate.update("insert into TestSubmission(account_id, test_id, url, is_passed, description) " +
                    "values(?, ?, ?, 3, ?)", accountId, examId, url, description);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new CustomException(ExceptionType.DATABASE_ERROR);
        }
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
            String explain = rs.getString("explain");
            Integer price = rs.getInt("price");
            return new Exam(id, subject_id, url, name, explain, price);
        };
    }

}
