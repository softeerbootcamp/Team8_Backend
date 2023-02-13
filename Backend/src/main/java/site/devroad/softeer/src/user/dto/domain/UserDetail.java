package site.devroad.softeer.src.user.dto.domain;

public class UserDetail {
    /* {
             "id" : "1",
             "email" : "hello@naver.com",
             "roadmapId" : "2",
             "userName" : "jm"
          }, {...}*/

    public UserDetail(Long id, String email, Long roadmapId, String userName) {
        this.id = id;
        this.email = email;
        this.roadmapId = roadmapId;
        this.userName = userName;
    }

    private final Long id;
    private final String email;
    private final Long roadmapId;
    private final String userName;

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Long getRoadmapId() {
        return roadmapId;
    }

    public String getUserName() {
        return userName;
    }
}
