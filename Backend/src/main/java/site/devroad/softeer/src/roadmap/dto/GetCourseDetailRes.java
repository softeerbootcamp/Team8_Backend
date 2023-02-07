package site.devroad.softeer.src.roadmap.dto;

import site.devroad.softeer.src.roadmap.dto.subdto.ChapterDetail;

import java.util.List;

public class GetCourseDetailRes {
    private final List<ChapterDetail> chapters;
    private final boolean success;
    private final Long curChapterId;

    public GetCourseDetailRes(List<ChapterDetail> chapters, Long curChapterId) {
        this.chapters = chapters;
        this.success = true;
        this.curChapterId = curChapterId;
    }

    public List<ChapterDetail> getChapters() {
        return chapters;
    }

    public boolean isSuccess() {
        return success;
    }

    public Long getCurChapterId() {
        return curChapterId;
    }
}
