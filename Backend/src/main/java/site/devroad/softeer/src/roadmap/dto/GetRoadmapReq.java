package site.devroad.softeer.src.roadmap.dto;

public class GetRoadmapReq {
    String jwt;

    public GetRoadmapReq(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
