package site.devroad.softeer.src.roadmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
import site.devroad.softeer.src.roadmap.model.Roadmap;
import site.devroad.softeer.src.roadmap.model.SubjectToRoadmap;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class RoadmapRepo {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RoadmapRepo(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Optional<Roadmap> findRoadmapById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM Roadmap WHERE id = ?", roadmapRowMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void updateCurChapterId(Long roadmapId, Long curChapterId) {
        jdbcTemplate.update("UPDATE Roadmap SET chapter_id = ? WHERE id = ?", curChapterId, roadmapId);
    }
    public Optional<Roadmap> findRoadmapByAccountId(Long accountId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT r.* FROM Account a JOIN Roadmap r " +
                    "ON a.roadmap_id = r.id WHERE a.id = ?", roadmapRowMapper(), accountId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<List<SubjectToRoadmap>> findSTRById(Long roadmapId) {
        try {
            return Optional.of(jdbcTemplate.query("SELECT * FROM SubjectToRoadmap WHERE roadmap_id = ?"
                    , subjectToRoadmapRowMapper(), roadmapId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void addSubjectToRoadMap(Long roadmapId, Long subjectId, Integer seq) throws CustomException{
        //roadmap id 하나 받아서 seq해서 넣기.
        jdbcTemplate.update("insert into SubjectToRoadmap(roadmap_id, subject_id, sequence) values(?, ?, ?)", roadmapId, subjectId, seq);

    }

    public void deleteRoadmap(Long roadmapId){
        try{
            jdbcTemplate.update("update Account set roadmap_id = null where roadmap_id = ?", roadmapId);
            jdbcTemplate.update("delete from SubjectToRoadmap where roadmap_id = ?", roadmapId);
            jdbcTemplate.update("delete from Roadmap where id = ?", roadmapId);
        }catch(DataAccessException e){
            e.printStackTrace();
        }
    }


    public Long createRoadmap(String roadmapName) throws CustomException {
        jdbcTemplate.update("insert into Roadmap(name) values(?)", roadmapName);
        return jdbcTemplate.queryForObject("select max(id) from Roadmap", Long.class);
    }

    private RowMapper<Roadmap> roadmapRowMapper() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            Long chapterId = rs.getLong("chapter_id");
            return new Roadmap(id, name, chapterId);
        };
    }

    private RowMapper<SubjectToRoadmap> subjectToRoadmapRowMapper() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            Long roadmapId = rs.getLong("roadmap_id");
            Long subjectId = rs.getLong("subject_id");
            Long sequence = rs.getLong("sequence");
            return new SubjectToRoadmap(id, roadmapId, subjectId, sequence);
        };
    }


}
