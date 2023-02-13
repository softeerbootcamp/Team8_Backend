package site.devroad.softeer.src.roadmap.completedchapter;

import java.time.LocalDateTime;

public class CompletedChapter {
    private final Long id;
    private final Long accountId;
    private final Long chapterId;
    private final LocalDateTime createAt;

    public CompletedChapter(Long id, Long accountId, Long chapterId, LocalDateTime createAt) {
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }
}
