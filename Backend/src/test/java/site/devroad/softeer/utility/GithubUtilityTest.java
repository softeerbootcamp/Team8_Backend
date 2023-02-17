package site.devroad.softeer.utility;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class GithubUtilityTest {
    Logger logger = LoggerFactory.getLogger(GithubUtility.class);

    @Autowired
    GithubUtility githubUtility;

    @Test
    void testAllData() {
        Map<String, String> allJavaCodeFromRepo = githubUtility.getAllCodeFromRepo("rohsik2", "devroad-test", ".cpp");
        logger.info(allJavaCodeFromRepo.toString());
        assertThat(allJavaCodeFromRepo.keySet().size()).isEqualTo(2);
    }


    @Test
    void getProgrammingLanguageFromRepo() {
        String mainLanguage = githubUtility.getMainExtensionFromRepo("rohsik2", "devroad-test");
        logger.info(mainLanguage);
    }

//    @Test
//    void setIssueToRepo(){
//        String issueUrl = githubUtility.createIssue("rohsik2", "devroad-test", "this is my string", "this is my cotent");
//        logger.info(issueUrl);
//    }
}