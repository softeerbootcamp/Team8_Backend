package site.devroad.softeer.src.roadmap.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import site.devroad.softeer.src.roadmap.model.SubjectToRoadmap;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class SubjectToRoadmapRepo {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SubjectToRoadmapRepo(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Optional<SubjectToRoadmap> findSTR(Long roadmapId, Long subjectId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM SubjectToRoadmap " +
                            "WHERE roadmap_id = ? AND subject_id = ?",
                    subjectToRoadmapRepoRowMapper(), roadmapId, subjectId));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<SubjectToRoadmap> subjectToRoadmapRepoRowMapper() {
        return (rs, rowNum) -> {
            long id = rs.getLong("id");
            long roadmap_id = rs.getLong("roadmap_id");
            long subject_id = rs.getLong("subject_id");
            long sequence = rs.getLong("sequence");
            return new SubjectToRoadmap(id, roadmap_id, subject_id, sequence);
        };
    }
}
