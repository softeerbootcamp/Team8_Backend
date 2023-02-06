package site.devroad.softeer.src.roadmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.src.roadmap.dto.GetRoadmapRes;
import site.devroad.softeer.utility.JwtUtility;

import java.util.List;
import java.util.Map;

@RestController
public class RoadmapController {

    private final RoadmapService roadmapService;

    @Autowired
    public RoadmapController(RoadmapService roadmapService) {
        this.roadmapService = roadmapService;
    }

    @GetMapping("/api/roadmap")
    public ResponseEntity<?> getRoadmapSubjects(@RequestHeader(value = "jwt") String jwt) {
        try {
            JwtUtility.validateToken(jwt);
            Long accountId = JwtUtility.getAccountId(jwt);
            Map<String, List<List<Object>>> subjects = roadmapService.getSubjects(accountId);
            return new ResponseEntity<>(new GetRoadmapRes(subjects), HttpStatus.OK);
        } catch (CustomException e) {
            return e.getResponseEntity();
        }
    }
}
