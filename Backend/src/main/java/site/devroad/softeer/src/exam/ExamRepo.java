package site.devroad.softeer.src.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import site.devroad.softeer.src.exam.model.Exam;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class ExamRepo {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public ExamRepo(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public Optional<Exam> findTestBySubjectId(Long subjectId){
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from Test where subject_id = ?", testRowMapper(), subjectId));
        }
        catch(DataAccessException e){
            return Optional.empty();
        }
    }

    public Optional<Exam> findTestbyId(Long testId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from Test where id = ?", testRowMapper(), testId));
        }
        catch(DataAccessException e){
            return Optional.empty();
        }
    }

    public void delete(Long testId){
        jdbcTemplate.update("delete * from Test where id = ?", testId);
    }

    RowMapper<Exam> testRowMapper(){
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
