package site.devroad.softeer.src.exam.model;

import java.util.Arrays;
import java.util.Optional;

public enum SubmissionType {
    NONE(1, "NONE"),
    PURCHASED(2, "PURCHASED"),
    SUBMITTED(3, "SUBMITTED"),
    PASSED(4, "PASSED"),
    FAILED(5, "FAILED");

    Integer is_passed;
    String explain;

    SubmissionType(int is_passed, String explain){
        this.is_passed = is_passed;
        this.explain = explain;
    }

    public static SubmissionType getType(int is_passed){
        Optional<SubmissionType> optionalSubmissionType = Arrays.stream(SubmissionType.values()).filter(s -> s.is_passed == is_passed).findFirst();
        return optionalSubmissionType.orElse(NONE);
    }
}
