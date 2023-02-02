package site.devroad.softeer.src.roadmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.src.roadmap.model.Roadmap;
import site.devroad.softeer.utility.CustomRes;

@RestController
public class RoadmapController {

    private final RoadmapService roadmapService;

    @Autowired
    public RoadmapController(RoadmapService roadmapService) {
        this.roadmapService = roadmapService;
    }

    @GetMapping("/api/roadmap/{roadmapId}")
    public CustomRes<?> getRoadmap(@RequestHeader(value = "jwt") String jwt, @PathVariable("roadmapId") Long roadmapId) {
        try {
            Roadmap roadMap = roadmapService.getRoadMap(jwt, roadmapId);
            return new CustomRes<>(roadMap, HttpStatus.OK);
        } catch (CustomException e) {
            return e.getResponseEntity();
        }
    }
}
