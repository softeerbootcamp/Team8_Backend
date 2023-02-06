package site.devroad.softeer.src.user.dto;

public class GetDetailRes {
    private boolean success;
    private Long userId;
    private String userName;
    private Long roadmapId;
    private Long totalSubjectIdx;
    private Long curSubjectIdx;
    private Long totalChapterIdx;
    private Long curChapterIdx;

    public GetDetailRes(Long userId, String userName, Long roadmapId, Long totalSubjectIdx, Long curSubjectIdx, Long totalChapterIdx, Long curChapterIdx) {
        this.success = true;
        this.userId = userId;
        this.userName = userName;
        this.roadmapId = roadmapId;
        this.totalSubjectIdx = totalSubjectIdx;
        this.curSubjectIdx = curSubjectIdx;
        this.totalChapterIdx = totalChapterIdx;
        this.curChapterIdx = curChapterIdx;
    }

    public boolean isSuccess() {
        return success;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Long getRoadmapId() {
        return roadmapId;
    }

    public Long getTotalSubjectIdx() {
        return totalSubjectIdx;
    }

    public Long getCurSubjectIdx() {
        return curSubjectIdx;
    }

    public Long getTotalChapterIdx() {
        return totalChapterIdx;
    }

    public Long getCurChapterIdx() {
        return curChapterIdx;
    }
}
