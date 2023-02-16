package site.devroad.softeer.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class OpenAiUtilityTest {

    Logger logger = LoggerFactory.getLogger(OpenAiUtility.class);
    @Autowired
    OpenAiUtility openAiUtility;

    @Autowired
    GithubUtility githubUtility;


    //below code really sends issue to git. only uncomment to need testing
    //@Test
    void getCodeSummary() throws IOException {
        String username = "rohsik2";
        String repos = "devroad-test";
        Map<String, String> repo = githubUtility.getAllCodeFromRepo(username, repos, ".java");
        Map<String, String> message = new HashMap<>();
        for(String key : repo.keySet()) {
            String summary = openAiUtility.getCodeSummary(repo.get(key));
            message.put(key, summary);
        }
        String title = "Code Review from DevRoad";
        StringBuilder body = new StringBuilder();
        for(String key : message.keySet()){
            body.append("\n\n### filename : " + key + "\n");
            body.append(message.get(key) + "\n");
        }
        githubUtility.createIssue(username, repos, title, body.toString());
    }

}