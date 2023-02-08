package site.devroad.softeer.src.roadmap.dto.subdto;

import site.devroad.softeer.src.course.model.Chapter;

public class ChapterDetail {
    private Long chapterId;
    private String chapterName;
    private String chapterUrl;
    private String thumbnailUrl;
    private String explain;
    private Boolean finish;

    public ChapterDetail(Chapter chapter) {
        this.chapterId = chapter.getId();
        this.chapterName = chapter.getTitle();
        this.chapterUrl = chapter.getChapterUrl();
        this.thumbnailUrl = chapter.getThumbnailUrl();
        this.explain = chapter.getExplain();
        //TODO : chapter가 끝났는지 받아와야함.
        this.finish = true;
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
