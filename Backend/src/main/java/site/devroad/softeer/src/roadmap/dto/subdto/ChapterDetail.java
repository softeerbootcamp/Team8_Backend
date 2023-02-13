package site.devroad.softeer.src.roadmap.dto.subdto;

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
