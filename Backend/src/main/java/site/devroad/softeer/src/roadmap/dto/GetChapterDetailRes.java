package site.devroad.softeer.src.roadmap.dto;

import site.devroad.softeer.src.roadmap.dto.subdto.ChapterDetail;

public class GetChapterDetailRes {
    private boolean success;
    private ChapterDetail chapterDetail;

    public GetChapterDetailRes(ChapterDetail chapterDetail) {
        this.chapterDetail = chapterDetail;
    }

    public boolean isSuccess() {
        return success;
    }

    public ChapterDetail getChapterDetail() {
        return chapterDetail;
    }
}
