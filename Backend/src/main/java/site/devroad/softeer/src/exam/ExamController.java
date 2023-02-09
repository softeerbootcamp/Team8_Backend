package site.devroad.softeer.src.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.src.exam.dto.GetExamDetailRes;
import site.devroad.softeer.src.exam.dto.PostAssignSubmitReq;
import site.devroad.softeer.src.exam.dto.subdto.ExamDetail;
import site.devroad.softeer.src.roadmap.RoadmapService;
import site.devroad.softeer.src.user.UserService;
import site.devroad.softeer.utility.JwtUtility;

import java.util.Map;

@RestController
public class ExamController {
    private ExamService examService;
    private UserService userService;
    private JwtUtility jwtUtility;
    private RoadmapService roadmapService;

    @Autowired
    public ExamController(ExamService examService, UserService userService, JwtUtility jwtUtility, RoadmapService roadmapService) {
        this.examService = examService;
        this.userService = userService;
        this.jwtUtility = jwtUtility;
        this.roadmapService = roadmapService;
    }

    @PostMapping("/api/exam/{examId}")
    public ResponseEntity<?> purchaseExam(@PathVariable("examId") Long examId, @RequestHeader String jwt) {
        try {
            jwtUtility.validateToken(jwt);
            Long accountId = jwtUtility.getAccountId(jwt);
            examService.purchaseExam(accountId, examId);
            return new ResponseEntity<>(Map.of("success", true), HttpStatus.OK);
        } catch (CustomException e) {
            return e.getResponseEntity();
        }
    }

    @GetMapping("/api/exam/{examId}")
    public ResponseEntity<?> getExamDetail(@PathVariable("examId") Long examId, @RequestHeader String jwt) {
        try{
            jwtUtility.validateToken(jwt);
            Long accountId = jwtUtility.getAccountId(jwt);
            examService.checkExamPurchased(accountId, examId);
            ExamDetail examDetail = examService.getExamDetail(examId);
            return new ResponseEntity<>(new GetExamDetailRes(true, examDetail), HttpStatus.OK);
        } catch (CustomException e) {
            return e.getResponseEntity();
        }
    }

    @PostMapping("/api/exam/assignment")
    public ResponseEntity<?> assignmentSubmit(@RequestHeader String jwt, @RequestBody PostAssignSubmitReq req){
        try{
            jwtUtility.validateToken(jwt);
            Long accountId = jwtUtility.getAccountId(jwt);
            examService.checkExamPurchased(accountId, req.getExamId());
            examService.submitAssignment(accountId, req);
            return new ResponseEntity<>(Map.of("success", true), HttpStatus.CREATED);
        } catch (CustomException e) {
            return e.getResponseEntity();
        }
    }



}
