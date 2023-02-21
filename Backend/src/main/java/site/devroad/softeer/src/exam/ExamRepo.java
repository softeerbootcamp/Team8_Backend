package site.devroad.softeer.src.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import site.devroad.softeer.src.exam.dto.domain.ExamDetail;
import site.devroad.softeer.src.exam.model.Exam;
import site.devroad.softeer.src.exam.model.ExamMcq;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class ExamRepo {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ExamRepo(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public Optional<Exam> findExamBySubjectId(Long subjectId, String examType) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from Exam where subject_id = ? and type LIKE ?", examRowMapper(), subjectId, examType));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Exam> findExamBySubjectIdAndType(Long subjectId, String type) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from Exam where subject_id = ? and type = ?"
                    , examRowMapper(), subjectId, type));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Exam> findExamById(Long examId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from Exam where id = ?", examRowMapper(), examId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<ExamDetail> findExamDetailById(Long examId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT \n" +
                            "s.name AS subject_name,\n" +
                            "e.url AS url, \n" +
                            "e.name AS name,\n" +
                            "e.description AS description,\n" +
                            "e.type AS type\n" +
                            "FROM Subject s\n" +
                            "JOIN Exam e \n" +
                            "ON s.id = e.subject_id\n" +
                            "WHERE e.id = ?",
                    examDetailRowMapper(), examId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<ExamMcq> findQuestionsByExamId(Long examId) {
        return jdbcTemplate.query("SELECT mcq.* FROM ExamMcq mcq \n" +
                "JOIN Exam e ON e.id = mcq.exam_id \n" +
                "WHERE mcq.exam_id = ? \n" +
                "ORDER BY mcq.`sequence` ", examMcqRowMapper(), examId);
    }

    RowMapper<Exam> examRowMapper() {
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

    RowMapper<ExamDetail> examDetailRowMapper() {
        return (rs, rowNum) -> {
            String subjectName = rs.getString("subject_name");
            String url = rs.getString("url");
            String name = rs.getString("name");
            String description = rs.getString("description");
            String type = rs.getString("type");
            return ExamDetail.createFRQDetail(subjectName, url, name, description, type);
        };
    }

    RowMapper<ExamMcq> examMcqRowMapper() {
        return (rs, rowNum) -> {
            long id = rs.getLong("id");
            long examId = rs.getLong("exam_id");
            long sequence = rs.getLong("sequence");
            String title = rs.getString("title");
            String content = rs.getString("content");
            String[] choices = rs.getString("choices").split("\\|");
            List<String> choicesList = Arrays.asList(choices);
            int ans = rs.getInt("ans");
            return new ExamMcq(id, examId, sequence, title, content, choicesList, ans);
        };
    }
}
