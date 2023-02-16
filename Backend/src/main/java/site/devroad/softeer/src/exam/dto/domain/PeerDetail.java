package site.devroad.softeer.src.exam.dto.domain;

public class PeerDetail {
    private final String url;
    private final String username;
    private final String curSubejct;

    public PeerDetail(String url, String username, String curSubejct) {
        this.url = url;
        this.username = username;
        this.curSubejct = curSubejct;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getCurSubejct() {
        return curSubejct;
    }
}
