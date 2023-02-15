package site.devroad.softeer.utility;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class GithubUtilityTest {
    Logger logger = LoggerFactory.getLogger(GithubUtility.class);

    @Autowired
    GithubUtility githubUtility;

    @Test
    @DisplayName("github get test")
    void getFileTest() throws IOException {
        //given
        String gitUrl = "https://github.com/rohsik2/hello-world";
        String pathUrl = "main.py";
        if(false){
            githubUtility.createIssue(gitUrl, "really", "works");
        }

        //when
        String result = githubUtility.getOneFile(gitUrl, pathUrl);

        //then
        assertThat(result).isEqualTo("print(\"hello world\")\n");

    }

    @Test
    @DisplayName("github repo get all paths")
    void getFilePaths(){
        //given
        String gitUrl = "https://github.com/rohsik2/hello-world";

        //when
        List<String> paths = githubUtility.getPaths(gitUrl);

        //then
        logger.info(paths.toString());
    }
  
}