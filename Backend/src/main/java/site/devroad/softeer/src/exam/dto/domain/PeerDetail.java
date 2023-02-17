package site.devroad.softeer.src.exam.dto.domain;

public class PeerDetail {
    private final String url;
    private final String username;
    private final String curSubject;

    public PeerDetail(String url, String username, String curSubject) {
        this.url = url;
        this.username = username;
        this.curSubject = curSubject;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getCurSubject() {
        return curSubject;
    }
}
