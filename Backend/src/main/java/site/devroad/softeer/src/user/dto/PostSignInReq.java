package site.devroad.softeer.src.user.dto;

public class PostSignInReq {
    private String email;
    private String password;

    public PostSignInReq(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
