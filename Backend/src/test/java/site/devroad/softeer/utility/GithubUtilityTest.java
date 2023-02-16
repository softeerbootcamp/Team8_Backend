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
    @DisplayName("github repo get all paths")
    void getFilePaths(){
        //given
        String gitUrl = "https://github.com/rohsik2/devroad-test";

        //when
        List<String> paths = githubUtility.getPaths(gitUrl).files;

        //then
        logger.info(paths.toString());
    }

    @Test
    void testAllData() throws IOException {
        Map<String, String> allJavaCodeFromRepo = githubUtility.getAllCodeFromRepo("rohsik2", "devroad-test", ".java");
        logger.info(allJavaCodeFromRepo.toString());
        assertThat(allJavaCodeFromRepo.keySet().size()).isEqualTo(2);
    }
  
}