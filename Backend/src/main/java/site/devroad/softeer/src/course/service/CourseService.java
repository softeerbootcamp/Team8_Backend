package site.devroad.softeer.src.course.service;

import org.springframework.stereotype.Service;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.src.course.model.Chapter;
import site.devroad.softeer.src.course.repository.ChapterRepo;
import site.devroad.softeer.src.course.repository.CourseRepo;
import site.devroad.softeer.src.roadmap.dto.subdto.ChapterDetail;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    private final ChapterRepo chapterRepo;
    private final CourseRepo courseRepo;

    public CourseService(ChapterRepo chapterRepo, CourseRepo courseRepo) {
        this.chapterRepo = chapterRepo;
        this.courseRepo = courseRepo;
    }

    public List<ChapterDetail> getChapterDetails(Long courseId, Long accountId) throws CustomException {
        List<Chapter> chapters = chapterRepo.findChapterByCourseId(courseId);
        ArrayList<ChapterDetail> chapterDetails = new ArrayList<>();
        for (Chapter chapter : chapters) {
            chapterDetails.add(new ChapterDetail(chapter));
        }
        return chapterDetails;
    }
}
