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
import site.devroad.softeer.src.exam.dto.domain.ExamDetail;
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

    @GetMapping("/api/exam/{examId}")
    public ResponseEntity<?> getExamDetail(@PathVariable("examId") Long examId, @RequestAttribute Long accountId) {

        examService.checkExamPurchased(accountId);
        ExamDetail examDetail = examService.getExamDetail(examId);
        return new ResponseEntity<>(new GetExamDetailRes(true, examDetail), HttpStatus.OK);
    }

    @PostMapping("/api/exam/assignment")
    public ResponseEntity<?> assignmentSubmit(@RequestAttribute Long accountId, @RequestBody PostAssignSubmitReq req) {

        examService.checkExamPurchased(accountId);
        examService.submitAssignment(accountId, req);
        return new ResponseEntity<>(Map.of("success", true), HttpStatus.CREATED);
    }

    @GetMapping("/api/purchase/exam/success")
    public ResponseEntity<?> purchaseSuccess(@RequestParam String orderId, @RequestParam String paymentKey, @RequestParam Integer amount) {
        tossUtility.validateTossParams(orderId, paymentKey, amount);
        examService.makePurchasedByTossOrderId(orderId);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("Location", "https://devroad.site/roadmap");
        return new ResponseEntity<>("", map, HttpStatus.TEMPORARY_REDIRECT);
    }

    @GetMapping("/api/purchase/exam/fail")
    public ResponseEntity<?> purchaseFail() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("Location", "/roadmap");
        return new ResponseEntity<>("", map, HttpStatus.TEMPORARY_REDIRECT);
    }


}
