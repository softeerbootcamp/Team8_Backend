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
import java.util.Optional;

@Repository
public class UserRepo {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepo(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public LoginInfo addLoginInfo(String email, String password, Long accountId) {
        jdbcTemplate.update("INSERT INTO LoginInfo (email, password, account_id) VALUES (?, ?, ?)",
                email, password, accountId);
        return jdbcTemplate.queryForObject("SELECT * FROM LoginInfo WHERE email = ?", loginInfoRowMapper(), email);
    }

    public Account addAccountInfo(String name, String phone, String type) {
        jdbcTemplate.update("INSERT INTO Account (name, phone, type) VALUES (?, ?, ?)",
                name, phone, type);
        return jdbcTemplate.queryForObject("SELECT * FROM Account WHERE phone = ?", accountRowMapper(), phone);
    }

    public Optional<LoginInfo> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM LoginInfo WHERE email = ?", loginInfoRowMapper(), email));
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
