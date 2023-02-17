package site.devroad.softeer.src.roadmap.course;

import org.springframework.stereotype.Service;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
import site.devroad.softeer.src.roadmap.RoadmapRepo;
import site.devroad.softeer.src.roadmap.chapter.Chapter;
import site.devroad.softeer.src.roadmap.chapter.ChapterRepo;
import site.devroad.softeer.src.roadmap.completedchapter.CompletedChapter;
import site.devroad.softeer.src.roadmap.completedchapter.CompletedChapterRepo;
import site.devroad.softeer.src.roadmap.dto.domain.ChapterDetail;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    public static final Long FINISHED = -1L;
    private final RoadmapRepo roadmapRepo;
    private final ChapterRepo chapterRepo;
    private final CourseRepo courseRepo;
    private final CompletedChapterRepo completedChapter;

    public CourseService(RoadmapRepo roadmapRepo, ChapterRepo chapterRepo, CourseRepo courseRepo, CompletedChapterRepo completedChapter) {
        this.roadmapRepo = roadmapRepo;
        this.chapterRepo = chapterRepo;
        this.courseRepo = courseRepo;
        this.completedChapter = completedChapter;
    }

    public List<ChapterDetail> getChapterDetails(Long courseId){
        return chapterRepo.findChapterDetailByCourseId(courseId);
    }

    public Optional<Chapter> getNextChapter(Long chapterId){
        Optional<Chapter> chapterById = chapterRepo.findChapterById(chapterId);
        if (chapterById.isEmpty()) {
            throw new CustomException(ExceptionType.CHAPTER_NOT_FOUND);
        }
        Chapter chapter = chapterById.get();
        Long courseId = chapter.getCourseId();
        Integer sequence = chapter.getSequence();
        return chapterRepo.findNextChapter(courseId, sequence + 1);
    }

    public Chapter getChapter(Long chapterId){
        Optional<Chapter> chapterById = chapterRepo.findChapterById(chapterId);
        if (chapterById.isEmpty()) {
            throw new CustomException(ExceptionType.CHAPTER_NOT_FOUND);
        }
        return chapterById.get();
    }

    public ChapterDetail getChapterDetail(Long chapterId){
        Optional<ChapterDetail> chapterById = chapterRepo.findChapterDetailById(chapterId);
        if (chapterById.isEmpty()) {
            throw new CustomException(ExceptionType.CHAPTER_NOT_FOUND);
        }
        return chapterById.get();
    }

    public Boolean getCourseFinished(Long chapterId){
        Optional<Chapter> nextChapter = getNextChapter(chapterId);
        return nextChapter.isEmpty();
    }

    public Long getNextChapterId(Long accountId, Long chapterId)  {
        Optional<Chapter> nextChapter = getNextChapter(chapterId);
        //completed chapter db에 chapter 넣어줌
        Optional<CompletedChapter> completedChapterOptional = completedChapter.readCompletedChapter(accountId, chapterId);
        if (completedChapterOptional.isPresent() && nextChapter.isPresent())
            return nextChapter.get().getId();
        completedChapter.createCompletedChapter(accountId, chapterId);
        if (nextChapter.isPresent()) {
            return nextChapter.get().getId();
        }
        return FINISHED;
    }

    public int getChapterCountByCourseId(Long courseId) {
        List<Chapter> chapters = chapterRepo.findChaptersByCourseId(courseId);
        return chapters.size();
    }
}
