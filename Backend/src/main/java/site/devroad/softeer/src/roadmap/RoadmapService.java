package site.devroad.softeer.src.roadmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;
import site.devroad.softeer.src.roadmap.model.Roadmap;

import java.util.Optional;

@Service
public class RoadmapService {
    @Autowired
    RoadmapRepo roadmapRepo;

    public Roadmap getRoadMap(String jwt, Long roadmapId) throws CustomException {
        //TODO: req의 JWT 검증
        Optional<Roadmap> roadmap = roadmapRepo.findById(roadmapId);
        if (roadmap.isEmpty())
            throw new CustomException(ExceptionType.ROADMAP_NOT_FOUND);
        return roadmap.get();
    }
}
