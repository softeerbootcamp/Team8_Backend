package site.devroad.softeer.src.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.src.exam.dto.GetExamDetailRes;
import site.devroad.softeer.src.exam.dto.PostAssignSubmitReq;
import site.devroad.softeer.src.exam.dto.subdto.ExamDetail;
import site.devroad.softeer.src.exam.model.Exam;
import site.devroad.softeer.src.roadmap.RoadmapService;
import site.devroad.softeer.src.user.UserService;
import site.devroad.softeer.utility.JwtUtility;
import site.devroad.softeer.utility.TossUtility;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ExamController {
    private ExamService examService;
    private UserService userService;
    private JwtUtility jwtUtility;
    private RoadmapService roadmapService;
    private TossUtility tossUtility;

    @Autowired
    public ExamController(ExamService examService, UserService userService, JwtUtility jwtUtility, RoadmapService roadmapService, TossUtility tossUtility) {
        this.examService = examService;
        this.userService = userService;
        this.jwtUtility = jwtUtility;
        this.roadmapService = roadmapService;
        this.tossUtility = tossUtility;
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
        try {
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
    public ResponseEntity<?> assignmentSubmit(@RequestHeader String jwt, @RequestBody PostAssignSubmitReq req) {
        try {
            jwtUtility.validateToken(jwt);
            Long accountId = jwtUtility.getAccountId(jwt);
            examService.checkExamPurchased(accountId, req.getExamId());
            examService.submitAssignment(accountId, req);
            return new ResponseEntity<>(Map.of("success", true), HttpStatus.CREATED);
        } catch (CustomException e) {
            return e.getResponseEntity();
        }


    }

    @GetMapping("/api/exam/purchase/success")
    public ResponseEntity<?> purchaseSuccess(@RequestParam String orderId, @RequestParam String paymentKey, @RequestParam Integer amount) {
        try {
            tossUtility.validateTossParams(orderId, paymentKey, amount);
            MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
            map.add("Location","/roadmap");
            examService.makePurchasedByTossOrderId(orderId);
            return new ResponseEntity<>("",map,HttpStatus.TEMPORARY_REDIRECT);

        } catch (CustomException e) {
            return e.getResponseEntity();
        }

    }


}
