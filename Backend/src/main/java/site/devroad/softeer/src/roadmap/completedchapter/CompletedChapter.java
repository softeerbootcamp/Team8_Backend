package site.devroad.softeer.src.roadmap.completedchapter;

import java.sql.Date;

public class CompletedChapter {
    private final Long id;
    private final Long accountId;
    private final Long chapterId;
    private final Date createAt;

    public CompletedChapter(Long id, Long accountId, Long chapterId, Date createAt) {
        this.id = id;
        this.accountId = accountId;
        this.chapterId = chapterId;
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public Date getCreateAt() {
        return createAt;
    }
}
