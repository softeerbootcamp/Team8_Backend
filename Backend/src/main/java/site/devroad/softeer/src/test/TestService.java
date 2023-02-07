package site.devroad.softeer.src.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
import site.devroad.softeer.src.course.model.Subject;
import site.devroad.softeer.src.course.repository.SubjectRepo;
import site.devroad.softeer.src.test.model.SubmissionType;
import site.devroad.softeer.src.test.model.Test;
import site.devroad.softeer.src.test.model.TestSubmission;

import java.util.Optional;

@Service
public class TestService {

    private static Logger logger = LoggerFactory.getLogger(TestService.class);
    private TestRepo testRepo;
    private SubjectRepo subjectRepo;
    private TestSubmissionRepo testSubmissionRepo;
    @Autowired
    public TestService(TestRepo testRepo, SubjectRepo subjectRepo, TestSubmissionRepo testSubmissionRepo){
        this.testRepo = testRepo;
        this.subjectRepo = subjectRepo;
        this.testSubmissionRepo = testSubmissionRepo;
    }

    public Boolean isUserPassedTest(Long subjectId, Long accountId) throws CustomException {

        //subjectId -> subject
        Optional<Subject> optionalSubject = subjectRepo.findById(subjectId);
        if(optionalSubject.isEmpty())
            throw new CustomException(ExceptionType.SUBJECT_NOT_FOUND);

        Subject subject = optionalSubject.get();

        logger.debug("subject id : {}, subject explain : {}",  subject.getId(), subject.getExplain());

        //subject.id -> test
        Optional<Test> optionalTest = testRepo.findTestBySubjectId(subject.getId());
        if(optionalTest.isEmpty())
            throw new CustomException(ExceptionType.TEST_NOT_FOUND);

        Test test = optionalTest.get();
        logger.debug("test id : {}, test explain : {}, accountId : {}",  test.getId(), test.getExplain(), accountId);
        //test.id + account.id -> testSubmission
        Optional<TestSubmission> testSubmission = testSubmissionRepo.findByTestIdAndAccountId(test.getId(), accountId);

        logger.debug("submit id : {}, {}", testSubmission.get().getId(), testSubmission.get().getSubmissionType());
        return testSubmission.filter(submission -> submission.getSubmissionType() == SubmissionType.PASSED).isPresent();


    }

}
