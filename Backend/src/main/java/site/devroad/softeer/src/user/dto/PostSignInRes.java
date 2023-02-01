package site.devroad.softeer.src.user.dto;

public class PostSignInRes {
    private boolean isSucess;
    private String jwt;

    public PostSignInRes(String jwt) {
        this.isSucess = true;
        this.jwt = jwt;
    }
}
