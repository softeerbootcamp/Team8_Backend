package site.devroad.softeer.src.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
import site.devroad.softeer.src.course.model.Subject;
import site.devroad.softeer.src.course.repository.SubjectRepo;
import site.devroad.softeer.src.exam.model.SubmissionType;
import site.devroad.softeer.src.exam.model.Exam;
import site.devroad.softeer.src.exam.model.ExamSubmission;

import java.util.Optional;

@Service
public class ExamService {

    private static Logger logger = LoggerFactory.getLogger(ExamService.class);
    private ExamRepo examRepo;
    private SubjectRepo subjectRepo;
    private ExamSubmissionRepo examSubmissionRepo;
    @Autowired
    public ExamService(ExamRepo examRepo, SubjectRepo subjectRepo, ExamSubmissionRepo examSubmissionRepo){
        this.examRepo = examRepo;
        this.subjectRepo = subjectRepo;
        this.examSubmissionRepo = examSubmissionRepo;
    }

    public Boolean isUserPassedTest(Long subjectId, Long accountId) throws CustomException {

        //subjectId -> subject
        Optional<Subject> optionalSubject = subjectRepo.findById(subjectId);
        if(optionalSubject.isEmpty())
            throw new CustomException(ExceptionType.SUBJECT_NOT_FOUND);

        Subject subject = optionalSubject.get();

        logger.debug("subject id : {}, subject explain : {}",  subject.getId(), subject.getExplain());

        //subject.id -> test
        Optional<Exam> optionalTest = examRepo.findTestBySubjectId(subject.getId());
        if(optionalTest.isEmpty())
            throw new CustomException(ExceptionType.TEST_NOT_FOUND);

        Exam exam = optionalTest.get();
        logger.debug("test id : {}, test explain : {}, accountId : {}",  exam.getId(), exam.getExplain(), accountId);
        //test.id + account.id -> testSubmission
        Optional<ExamSubmission> testSubmission = examSubmissionRepo.findByTestIdAndAccountId(exam.getId(), accountId);

        logger.debug("submit id : {}, {}", testSubmission.get().getId(), testSubmission.get().getSubmissionType());
        return testSubmission.filter(submission -> submission.getSubmissionType() == SubmissionType.PASSED).isPresent();


    }

}
