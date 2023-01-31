package site.devroad.softeer.src.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepoTest {
    @Autowired
    UserRepo userRepo;

    @Test
    void addAccountTest() {
        userRepo.addAccountInfo("asdf", "01012341234", "student");
    }
}
