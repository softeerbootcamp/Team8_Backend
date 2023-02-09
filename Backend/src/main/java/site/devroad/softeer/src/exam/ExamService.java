package site.devroad.softeer.src.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
import site.devroad.softeer.src.course.model.Subject;
import site.devroad.softeer.src.course.repository.SubjectRepo;
import site.devroad.softeer.src.exam.dto.PostAssignSubmitReq;
import site.devroad.softeer.src.exam.dto.subdto.ExamDetail;
import site.devroad.softeer.src.exam.model.Exam;
import site.devroad.softeer.src.exam.model.ExamSubmission;
import site.devroad.softeer.src.exam.model.SubmissionType;
import site.devroad.softeer.src.user.UserRepo;
import site.devroad.softeer.src.user.model.Account;

import java.util.Optional;

@Service
public class ExamService {

    private static Logger logger = LoggerFactory.getLogger(ExamService.class);
    private ExamRepo examRepo;
    private SubjectRepo subjectRepo;
    private ExamSubmissionRepo examSubmissionRepo;
    private UserRepo userRepo;

    @Autowired
    public ExamService(ExamRepo examRepo, SubjectRepo subjectRepo, ExamSubmissionRepo examSubmissionRepo, UserRepo userRepo) {
        this.examRepo = examRepo;
        this.subjectRepo = subjectRepo;
        this.examSubmissionRepo = examSubmissionRepo;
        this.userRepo = userRepo;
    }

    public Boolean isUserPassedExam(Long subjectId, Long accountId) throws CustomException {

        //subjectId -> subject
        Optional<Subject> optionalSubject = subjectRepo.findById(subjectId);
        if (optionalSubject.isEmpty())
            throw new CustomException(ExceptionType.SUBJECT_NOT_FOUND);

        Subject subject = optionalSubject.get();

        logger.debug("subject id : {}, subject description : {}", subject.getId(), subject.getDescription());

        //subject.id -> exam
        Optional<Exam> optionalExam = examRepo.findExamBySubjectId(subject.getId());
        if (optionalExam.isEmpty())
            throw new CustomException(ExceptionType.EXAM_NOT_FOUND);

        Exam exam = optionalExam.get();
        logger.debug("exam id : {}, exam description : {}, accountId : {}", exam.getId(), exam.getDescription(), accountId);
        //exam.id + account.id -> examSubmission
        Optional<ExamSubmission> examSubmission = examSubmissionRepo.findByExamIdAndAccountId(exam.getId(), accountId);

        logger.debug("submit id : {}, {}", examSubmission.get().getId(), examSubmission.get().getSubmissionType());
        return examSubmission.filter(submission -> submission.getSubmissionType() == SubmissionType.PASSED).isPresent();


    }

    public void purchaseExam(Long accountId, Long examId) throws CustomException {
        Account account = userRepo.findAccountById(accountId);
        Optional<Exam> exam = examRepo.findExamById(examId);
        examRepo.addExamToAccount(accountId, examId);
    }

    public void checkExamPurchased(Long accountId, Long examId) throws CustomException {
        if (!examRepo.isExamPurchased(accountId, examId)) {
            throw new CustomException(ExceptionType.EXAM_NOT_PURCHASED);
        }
    }

    public ExamDetail getExamDetail(Long examId) throws CustomException {
        Optional<ExamDetail> examDetailById = examRepo.findExamDetailById(examId);
        if (examDetailById.isEmpty()) {
            throw new CustomException(ExceptionType.EXAM_NOT_FOUND);
        }
        return examDetailById.get();
    }

    public void submitAssignment(Long accountId, PostAssignSubmitReq req) throws CustomException{
        examRepo.addExamSubmission(accountId, req.getExamId(), req.getUrl(), req.getDescription());
    }
    public void makePurchasedByTossOrderId(String orderId) throws CustomException{
        //   orderId : accountId + "_" + examId + "_" + randomStr,
        String[] parsedOrderId = orderId.split("_");
        Long accountId = Long.valueOf(parsedOrderId[0]);
        Long examId = Long.valueOf(parsedOrderId[1]);
        purchaseExam(accountId,examId);

    }

}
