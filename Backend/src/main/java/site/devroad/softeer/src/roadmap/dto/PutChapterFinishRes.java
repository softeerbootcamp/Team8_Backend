package site.devroad.softeer.src.roadmap.dto;

public class PutChapterFinishRes {
    private Boolean success;
    private Boolean isCourseFinished;
    private Long nextChapterId;

    public PutChapterFinishRes(Boolean isCourseFinished, Long nextChapterId) {
        this.success = true;
        this.isCourseFinished = isCourseFinished;
        this.nextChapterId = nextChapterId;
    }

    public Boolean getSuccess() {
        return success;
    }

    public Boolean getCourseFinished() {
        return isCourseFinished;
    }

    public Long getNextChapterId() {
        return nextChapterId;
    }
}
