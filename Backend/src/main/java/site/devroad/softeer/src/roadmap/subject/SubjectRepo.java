package site.devroad.softeer.src.roadmap.subject;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
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
            String description = rs.getString("description");
            return new Subject(id, name, description);
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

    public List<Subject> findAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM Subject", subjectRowMapper());
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    public List<Subject> findSubjectsByRoadmapId(Long roadmapId) {
        return jdbcTemplate.query("SELECT s.* FROM Subject s " +
                        "JOIN SubjectToRoadmap rs " +
                        "ON s.id = rs.subject_id " +
                        "WHERE rs.roadmap_id = ?",
                subjectRowMapper(), roadmapId);
    }
}
