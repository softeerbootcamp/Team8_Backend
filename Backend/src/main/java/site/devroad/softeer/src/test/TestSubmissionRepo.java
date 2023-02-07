package site.devroad.softeer.src.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import site.devroad.softeer.src.test.model.SubmissionType;
import site.devroad.softeer.src.test.model.TestSubmission;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class TestSubmissionRepo {
    private static Logger logger = LoggerFactory.getLogger(TestSubmission.class);

    JdbcTemplate jdbcTemplate;

    public TestSubmissionRepo(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public Optional<TestSubmission> findById(Long id){
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from TestSubmission where id = ?",
                    testSubmissionRowMapper(), id));
        }catch
        (DataAccessException e){
            return Optional.empty();
        }
    }

    public Optional<TestSubmission> findByTestIdAndAccountId(Long testId, Long accountId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select * from TestSubmission where test_id = ? and account_id = ?",
                    testSubmissionRowMapper(),
                    testId, accountId
            ));
        }
        catch(DataAccessException e){
            return Optional.empty();
        }
    }


    public RowMapper<TestSubmission> testSubmissionRowMapper(){
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            Long accountId = rs.getLong("account_id");
            Long testId = rs.getLong("test_id");
            String url = rs.getString("url");
            int pass_code = rs.getInt("is_passed");
            SubmissionType type = SubmissionType.getType(pass_code);
            String explain = rs.getString("explain");
            return new TestSubmission(id, accountId, testId, url, type, explain);
        };
    }
}
