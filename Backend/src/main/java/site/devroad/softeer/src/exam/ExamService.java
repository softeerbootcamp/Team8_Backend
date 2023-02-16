package site.devroad.softeer.src.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
import site.devroad.softeer.src.exam.dto.*;
import site.devroad.softeer.src.exam.dto.domain.Assignment;
import site.devroad.softeer.src.exam.dto.domain.ExamDetail;
import site.devroad.softeer.src.exam.dto.domain.MultiChoiceQuestion;
import site.devroad.softeer.src.exam.dto.domain.PeerDetail;
import site.devroad.softeer.src.exam.model.Exam;
import site.devroad.softeer.src.exam.model.ExamMcq;
import site.devroad.softeer.src.exam.model.ExamSubmission;
import site.devroad.softeer.src.exam.model.SubmissionType;
import site.devroad.softeer.src.roadmap.subject.Subject;
import site.devroad.softeer.src.roadmap.subject.SubjectRepo;
import site.devroad.softeer.src.user.UserRepo;
import site.devroad.softeer.src.user.model.Account;

import java.util.*;

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

    public Boolean isUserPassedExam(Long subjectId, Long accountId) {

        //subjectId -> subject
        Optional<Subject> optionalSubject = subjectRepo.findById(subjectId);
        if (optionalSubject.isEmpty())
            throw new CustomException(ExceptionType.SUBJECT_NOT_FOUND);

        Subject subject = optionalSubject.get();

        logger.warn("subject id : {}, subject description : {}", subject.getId(), subject.getDescription());

        //subject.id -> exam
        Optional<Exam> optionalExam = examRepo.findExamBySubjectId(subject.getId(), "MCQ");
        if (optionalExam.isEmpty())
            throw new CustomException(ExceptionType.EXAM_NOT_FOUND);

        Exam exam = optionalExam.get();
        logger.warn("exam id : {}, exam description : {}, accountId : {}", exam.getId(), exam.getDescription(), accountId);
        //exam.id + account.id -> examSubmission
        Optional<ExamSubmission> examSubmission = examSubmissionRepo.findByExamIdAndAccountId(exam.getId(), accountId);

        logger.warn("submit id : {}, {}", examSubmission.get().getId(), examSubmission.get().getSubmissionType());
        return examSubmission.filter(submission -> submission.getSubmissionType() == SubmissionType.PASSED).isPresent();


    }

    public ExamDetail getExamDetail(Long examId) {
        Optional<ExamDetail> examDetailById = examRepo.findExamDetailById(examId);
        if (examDetailById.isEmpty()) {
            throw new CustomException(ExceptionType.EXAM_NOT_FOUND);
        }
        ExamDetail examDetail = examDetailById.get();
        String type = examDetail.getType();
        if (type.equals("MCQ")) {
            List<ExamMcq> questionsByExamId = examRepo.findQuestionsByExamId(examId);
            return ExamDetail.createMCQDetail(examDetail, getAns(questionsByExamId), getMCQs(questionsByExamId));
        }
        return examDetail;
    }

    public List<Integer> getAns(List<ExamMcq> questions) {
        List<Integer> ans = new ArrayList<>();
        for (ExamMcq examMcq : questions) {
            ans.add(examMcq.getAns());
        }
        return ans;
    }

    public List<MultiChoiceQuestion> getMCQs(List<ExamMcq> questions) {
        List<MultiChoiceQuestion> ans = new ArrayList<>();
        for (ExamMcq examMcq : questions) {
            if (examMcq == null)
                continue;
            ans.add(new MultiChoiceQuestion(examMcq));
        }
        return ans;
    }

    public void checkExamPurchased(Long accountId) {
        if (!userRepo.isUserSubscribed(accountId)) {
            throw new CustomException(ExceptionType.EXAM_NOT_PURCHASED);
        }
    }

    public void submitAssignment(Long accountId, PostAssignSubmitReq req) {
        examRepo.addExamSubmission(accountId, req.getExamId(), req.getUrl(), req.getDescription());
    }

    public void makePurchasedByTossOrderId(String orderId) {
        //   orderId : accountId + "_" + examId + "_" + randomStr,
        String[] parsedOrderId = orderId.split("_");
        Long accountId = Long.valueOf(parsedOrderId[0]);
        if (!userRepo.isUserSubscribed(accountId)) {
            userRepo.doSubscribe(accountId);
        }
        userRepo.extendSubscribeEndDate(accountId, 31);
    }

    public PutExamDetailRes getExamDetailRes(PutExamDetailReq req, Long accountId) {
        Boolean result = req.getResult();
        Long examId = req.getExamId();
        SubmissionType submissionType = result ? SubmissionType.PASSED : SubmissionType.FAILED;
        examSubmissionRepo.updateByExamIdAndAccountId(examId, accountId, submissionType);
        return new PutExamDetailRes();
    }

    public GetAssignmentDetail getAssignmentDetail(Long examSubmissionId) {
        Optional<ExamSubmission> examSubmissionById = examSubmissionRepo.findExamSubmissionById(examSubmissionId);
        if (examSubmissionById.isEmpty())
            throw new CustomException(ExceptionType.EXAM_SUBMISSION_NOT_FOUND);
        ExamSubmission examSubmission = examSubmissionById.get();
        Account accountById = userRepo.findAccountById(examSubmission.getId());
        Assignment assignment = new Assignment(examSubmission, accountById.getName());
        return new GetAssignmentDetail(assignment);
    }
    public GetPeerDetail getPeerDetail(Long examId){

        List<PeerDetail> peerList = userRepo.findPeerDetailByExamId(examId);
        Collections.shuffle(peerList);
        return new GetPeerDetail(true , peerList.subList(0,2));
    }
}