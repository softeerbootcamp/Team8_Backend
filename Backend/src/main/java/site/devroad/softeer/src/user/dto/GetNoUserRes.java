package site.devroad.softeer.src.user.dto;

import java.util.List;

public class GetNoUserRes {
    Boolean success;
    List<String> users;

    public GetNoUserRes(Boolean success, List<String> users) {
        this.success = success;
        this.users = users;
    }

    public Boolean getSuccess() {
        return success;
    }

    public List<String> getUsers() {
        return users;
    }
}
