package site.devroad.softeer.src.roadmap.chapter;

import org.springframework.stereotype.Service;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;

import java.util.Optional;

@Service
public class ChapterService {
    private final ChapterRepo chapterRepo;

    public ChapterService(ChapterRepo chapterRepo) {
        this.chapterRepo = chapterRepo;
    }

    public Chapter getChapter(Long chapterId) throws CustomException {
        Optional<Chapter> chapterById = chapterRepo.findChapterById(chapterId);
        if (chapterById.isEmpty()) {
            throw new CustomException(ExceptionType.CHAPTER_NOT_FOUND);
        }
        return chapterById.get();
    }
}
