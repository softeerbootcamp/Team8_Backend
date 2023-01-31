package site.devroad.softeer.src.user.model;

public class LoginInfo {
    private Long id;
    private String email;
    private String password;
    private Long accountId;

    public LoginInfo(Long id, String email, String password, Long accountId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

}
