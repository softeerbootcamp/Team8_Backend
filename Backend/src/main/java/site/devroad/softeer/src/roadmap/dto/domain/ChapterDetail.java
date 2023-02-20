package site.devroad.softeer.src.roadmap.dto.domain;

import site.devroad.softeer.src.roadmap.chapter.Chapter;

public class ChapterDetail {
    private Long chapterId;
    private String chapterName;
    private String chapterUrl;
    private String thumbnailUrl;
    private String explain;
    private Boolean finish;

    public ChapterDetail(Long chapterId, String chapterName, String chapterUrl, String thumbnailUrl, String explain, Boolean finish) {
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.chapterUrl = chapterUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.explain = explain;
        this.finish = finish;
    }

    public ChapterDetail(Chapter chapter) {
        this.chapterId = chapter.getId();
        this.chapterName = chapter.getTitle();
        this.chapterUrl = chapter.getChapterUrl();
        this.thumbnailUrl = chapter.getThumbnailUrl();
        this.explain = chapter.getDescription();
        this.finish = false;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public String getChapterUrl() {
        return chapterUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getExplain() {
        return explain;
    }

    public Boolean getFinish() {
        return finish;
    }
}
