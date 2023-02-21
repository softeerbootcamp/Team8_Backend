package site.devroad.softeer.src.user;

import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql("classpath:create")
class UserRepoTest {

}
