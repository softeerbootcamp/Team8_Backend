package site.devroad.softeer.src.roadmap.dto;

import site.devroad.softeer.src.course.model.Chapter;

public class GetChapterDetailRes {
    private boolean success;
    private Chapter chapter;

    public GetChapterDetailRes(Chapter chapter) {
        this.chapter = chapter;
    }

    public boolean isSuccess() {
        return success;
    }

    public Chapter getChapter() {
        return chapter;
    }
}
