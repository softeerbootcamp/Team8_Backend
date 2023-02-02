package site.devroad.softeer.src.roadmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import site.devroad.softeer.src.roadmap.model.Roadmap;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class RoadmapRepo {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RoadmapRepo(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Optional<Roadmap> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM Roadmap WHERE id = ?", roadmapRowMapper(), id));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Roadmap> roadmapRowMapper() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            Long chapterId = rs.getLong("chapter_id");
            return new Roadmap(id, name, chapterId);
        };
    }
}
