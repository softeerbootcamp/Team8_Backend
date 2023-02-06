package site.devroad.softeer.src.test;

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

    JdbcTemplate jdbcTemplate;

    public TestSubmissionRepo(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
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
            SubmissionType type = SubmissionType.getType(rs.getInt("is_passed"));
            return new TestSubmission(id, accountId, testId, url, type);
        };
    }
}
