package site.devroad.softeer.src.user.dto;

public class GetDetailRes {
    private boolean success;
    private Long userId;
    private String userName;
    private Long roadmapId;
    private Long totalSubjectIdx;
    private Long curSubjectIdx;
    private Float chapterPercent;
    private Long nextChapterPK;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getRoadmapId() {
        return roadmapId;
    }

    public void setRoadmapId(Long roadmapId) {
        this.roadmapId = roadmapId;
    }

    public Long getTotalSubjectIdx() {
        return totalSubjectIdx;
    }

    public void setTotalSubjectIdx(Long totalSubjectIdx) {
        this.totalSubjectIdx = totalSubjectIdx;
    }

    public Long getCurSubjectIdx() {
        return curSubjectIdx;
    }

    public void setCurSubjectIdx(Long curSubjectIdx) {
        this.curSubjectIdx = curSubjectIdx;
    }

    public Float getChapterPercent() {
        return chapterPercent;
    }

    public void setChapterPercent(Float chapterPercent) {
        this.chapterPercent = chapterPercent;
    }

    public Long getNextChapterPK() {
        return nextChapterPK;
    }

    public void setNextChapterPK(Long nextChapterPK) {
        this.nextChapterPK = nextChapterPK;
    }
}
