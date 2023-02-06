package site.devroad.softeer.src.test;

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

    private TestRepo testRepo;
    private SubjectRepo subjectRepo;
    private TestSubmissionRepo testSubmissionRepo;
    @Autowired
    public TestService(TestRepo testRepo, SubjectRepo subjectRepo, TestSubmissionRepo testSubmissionRepo){
        this.testRepo = testRepo;
        this.subjectRepo = subjectRepo;
    }

    public Boolean isUserPassedTest(Long subjectId, Long accountId) throws CustomException {

        //subjectId -> subject
        Optional<Subject> optionalSubject = subjectRepo.findById(subjectId);
        if(optionalSubject.isEmpty())
            throw new CustomException(ExceptionType.SUBJECT_NOT_FOUND);

        Subject subject = optionalSubject.get();

        //subject.id -> test
        Optional<Test> optionalTest = testRepo.findTestBySubjectId(subject.getId());
        if(optionalTest.isEmpty())
            throw new CustomException(ExceptionType.TEST_NOT_FOUND);

        //test.id + account.id -> testSubmission
        Optional<TestSubmission> testSubmission = testSubmissionRepo.findByTestIdAndAccountId(optionalTest.get().getId(), accountId);
        return testSubmission.filter(submission -> submission.getSubmissionType() == SubmissionType.PASSED).isPresent();


    }

}
