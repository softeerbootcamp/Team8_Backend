package site.devroad.softeer.src.course.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import site.devroad.softeer.src.course.model.Subject;

import java.util.Optional;

@Repository
public class SubjectRepo {
    private final JdbcTemplate jdbcTemplate;

    public SubjectRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Subject> subjectRowMapper() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            String explain = rs.getString("explain");
            return new Subject(id, name, explain);
        };
    }

    public Optional<Subject> findById(Long subjectId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM Subject WHERE id = ?"
                    , subjectRowMapper(), subjectId));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
