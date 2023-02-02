package site.devroad.softeer.src.user.dto;

public class PostSignUpRes {
    private boolean success;
    private Long userId;

    public boolean isSuccess() {
        return success;
    }

    public Long getUserId() {
        return userId;
    }
}
