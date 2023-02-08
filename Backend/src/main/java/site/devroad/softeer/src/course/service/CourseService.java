package site.devroad.softeer.src.course.service;

import org.springframework.stereotype.Service;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
import site.devroad.softeer.src.course.model.Chapter;
import site.devroad.softeer.src.course.repository.ChapterRepo;
import site.devroad.softeer.src.course.repository.CourseRepo;
import site.devroad.softeer.src.roadmap.dto.subdto.ChapterDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    public static final Long FINISHED = -1L;
    private final ChapterRepo chapterRepo;
    private final CourseRepo courseRepo;

    public CourseService(ChapterRepo chapterRepo, CourseRepo courseRepo) {
        this.chapterRepo = chapterRepo;
        this.courseRepo = courseRepo;
    }

    public List<ChapterDetail> getChapterDetails(Long courseId) throws CustomException {
        List<Chapter> chapters = chapterRepo.findChapterByCourseId(courseId);
        ArrayList<ChapterDetail> chapterDetails = new ArrayList<>();
        for (Chapter chapter : chapters) {
            chapterDetails.add(new ChapterDetail(chapter));
        }
        return chapterDetails;
    }

    public Optional<Chapter> getNextChapter(Long chapterId) throws CustomException {
        Optional<Chapter> chapterById = chapterRepo.findChapterById(chapterId);
        if (chapterById.isEmpty()) {
            throw new CustomException(ExceptionType.CHAPTER_NOT_FOUND);
        }
        Chapter chapter = chapterById.get();
        Long courseId = chapter.getCourseId();
        Integer sequence = chapter.getSequence();
        return chapterRepo.findNextChapter(courseId, sequence + 1);
    }

    public Chapter getChapter(Long chapterId) throws CustomException {
        Optional<Chapter> chapterById = chapterRepo.findChapterById(chapterId);
        if (chapterById.isEmpty()) {
            throw new CustomException(ExceptionType.CHAPTER_NOT_FOUND);
        }
        return chapterById.get();
    }

    public Boolean getCourseFinished(Long chapterId) throws CustomException {
        Optional<Chapter> nextChapter = getNextChapter(chapterId);
        return nextChapter.isEmpty();
    }

    public Long getNextChapterId(Long chapterId) throws CustomException {
        Optional<Chapter> nextChapter = getNextChapter(chapterId);
        if (nextChapter.isPresent()) {
            return nextChapter.get().getId();
        }
        return FINISHED;
    }
}
