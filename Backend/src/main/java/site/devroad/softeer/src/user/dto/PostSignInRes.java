package site.devroad.softeer.src.user.dto;

public class PostSignInRes {
    private boolean success;
    private String jwt;

    public PostSignInRes(String jwt) {
        this.success = true;
        this.jwt = jwt;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getJwt() {
        return jwt;
    }
}
