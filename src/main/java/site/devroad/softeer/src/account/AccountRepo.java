package site.devroad.softeer.src.account;

import java.util.Collection;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import site.devroad.softeer.src.account.DTO.PostAccountReq;
import site.devroad.softeer.src.account.model.Account;

@Repository
public class AccountRepo {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountRepo(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Optional<Account> findById(Long id) {
        try{
            return Optional.ofNullable(
                jdbcTemplate.queryForObject("SELECT * FROM Account WHERE id = ?", accountRowMapper(), id));
        }
        catch (DataAccessException e)
        {
            return Optional.empty();
        }
    }

    //find by email
    public Optional<Account> findByEmail(String email) {
        try{
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM Account WHERE email = ?", accountRowMapper(), email));
        }
        catch(DataAccessException e)
        {
            return Optional.empty();
        }
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM Account WHERE id = ?", id);
    }

    public Collection<Account> getAll() {
        return jdbcTemplate.query("SELECT * FROM Account", accountRowMapper());
    }

    public void update(Account account) {
        jdbcTemplate.update("UPDATE Account SET name = ?, email = ?, password = ?, updated_at = ? WHERE id = ?",
                account.getName(), account.getEmail(), account.getPassword(), account.getUpdatedAt(), account.getId());
    }

    public Account addAccount(PostAccountReq account) {
        jdbcTemplate.update("INSERT INTO Account (name, email, password) VALUES (?, ?, ?,)",
                account.getName(), account.getEmail(), account.getPassword());
        return jdbcTemplate.queryForObject("SELECT * FROM Account WHERE email = ?", accountRowMapper(), account.getEmail());
    }

    private RowMapper<Account> accountRowMapper(){
        return ((rs, rowNum) -> {
            Account account = new Account();
            account.setId(rs.getLong("id"));
            account.setEmail(rs.getString("email"));
            account.setName(rs.getString("name"));
            account.setPassword(rs.getString("password"));
            account.setCreatedAt(rs.getTimestamp("created_at"));
            account.setUpdatedAt(rs.getTimestamp("updated_at"));
            return account;
        });
    }
}
