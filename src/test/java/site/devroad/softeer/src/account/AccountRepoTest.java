package site.devroad.softeer.src.account;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.devroad.softeer.src.account.model.Account;
@SpringBootTest
class AccountRepoTest {

    @Autowired
    AccountRepo accountRepo;

    @Test
    void findById() {
        Optional<Account> account = accountRepo.findById(1L);
        assertTrue(account.isPresent());
    }

    @Test
    void findByEmail() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void update() {
    }
}