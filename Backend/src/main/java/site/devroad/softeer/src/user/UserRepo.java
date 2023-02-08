package site.devroad.softeer.src.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import site.devroad.softeer.src.user.model.Account;
import site.devroad.softeer.src.user.model.LoginInfo;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepo {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepo(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public LoginInfo createLoginInfo(String email, String password, Long accountId) {
        jdbcTemplate.update("INSERT INTO LoginInfo (email, password, account_id) VALUES (?, ?, ?)",
                email, password, accountId);
        return jdbcTemplate.queryForObject("SELECT * FROM LoginInfo WHERE email = ?", loginInfoRowMapper(), email);
    }

    public Account createAccountInfo(String name, String phone, String type) {
        jdbcTemplate.update("INSERT INTO Account (name, phone, type) VALUES (?, ?, ?)",
                name, phone, type);
        return jdbcTemplate.queryForObject("SELECT * FROM Account WHERE phone = ?", accountRowMapper(), phone);
    }

    public void deleteLoginInfoById(Long id) {
        jdbcTemplate.update("DELETE FROM LoginInfo where id = ?", id);
    }

    public Account findAccountById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Account WHERE id = ?", accountRowMapper(), id);
    }

    public void deleteAccountById(Long id) {
        jdbcTemplate.update("DELETE FROM Account where id = ?", id);
    }

    public Optional<LoginInfo> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM LoginInfo WHERE email = ?", loginInfoRowMapper(), email));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public List<LoginInfo> findNoRoadmapUser() {
        try {
            return jdbcTemplate.query("SELECT l.* FROM LoginInfo l JOIN Account a " +
                    "ON l.account_id = a.id AND a.roadmap_id IS NULL", loginInfoRowMapper());
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    public Optional<Account> findByPhone(String phone) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM Account WHERE phone = ?", accountRowMapper(), phone));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<LoginInfo> loginInfoRowMapper() {
        return ((rs, rowNum) -> {
            Long id = (rs.getLong("id"));
            String email = (rs.getString("email"));
            String password = (rs.getString("password"));
            Long accountId = (rs.getLong("account_id"));
            return new LoginInfo(id, email, password, accountId);
        });
    }


    public void setRoadmap(Long id, Long roadmapId) {
        jdbcTemplate.update("UPDATE Account SET roadmap_id = ? WHERE id=?", roadmapId, id);
    }

    private RowMapper<Account> accountRowMapper() {
        return ((rs, rowNum) -> {
            Long id = (rs.getLong("id"));
            String name = (rs.getString("name"));
            Long roadMapId = (rs.getLong("roadmap_id"));
            String phone = (rs.getString("phone"));
            String type = (rs.getString("type"));
            Timestamp createdAt = (rs.getTimestamp("created_at"));
            Timestamp updatedAt = (rs.getTimestamp("updated_at"));
            return new Account(id, name, roadMapId, phone, type, createdAt, updatedAt);
        });
    }

}
