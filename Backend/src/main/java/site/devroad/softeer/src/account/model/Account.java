package site.devroad.softeer.src.account.model;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Account {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
