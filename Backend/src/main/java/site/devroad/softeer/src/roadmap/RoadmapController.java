package site.devroad.softeer.src.roadmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.src.roadmap.dto.GetRoadmapRes;

import java.util.List;
import java.util.Map;

@RestController
public class RoadmapController {

    private final RoadmapService roadmapService;

    @Autowired
    public RoadmapController(RoadmapService roadmapService) {
        this.roadmapService = roadmapService;
    }

    @GetMapping("/api/roadmap/{roadmapId}")
    public ResponseEntity<?> getRoadmapSubjects(@RequestHeader(value = "jwt") String jwt, @PathVariable("roadmapId") Long roadmapId) {
        try {
            Map<String, List<List<Object>>> subjects = roadmapService.getSubjects(jwt, roadmapId);
            return new ResponseEntity<>(new GetRoadmapRes(subjects), HttpStatus.OK);
        } catch (CustomException e) {
            return e.getResponseEntity();
        }
    }
}
