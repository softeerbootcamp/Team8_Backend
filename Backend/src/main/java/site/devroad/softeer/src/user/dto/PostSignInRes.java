package site.devroad.softeer.src.user.dto;

public class PostSignInRes {
    private boolean success;
    private String jwt;
    private boolean admin;

    public PostSignInRes(String jwt, boolean admin) {
        this.success = true;
        this.jwt = jwt;
        this.admin = admin;
    }

    public boolean isAdmin() {
        return admin;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getJwt() {
        return jwt;
    }
}
