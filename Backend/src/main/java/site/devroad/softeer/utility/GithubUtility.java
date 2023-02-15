package site.devroad.softeer.utility;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
public class GithubUtility {

    Logger logger = LoggerFactory.getLogger(GithubUtility.class);

    @Value("${github.apiKey}")
    private String gitApiKey;

    //create new Issue from originGitUrl and returns new Issue url;
    public String createIssue(String originGitUrl, String title, String content){
        if(originGitUrl.contains("issue"))
            throw new CustomException(ExceptionType.GITHUB_ISSUE_ALREADY_SENDED);

        try {
            int start_at = originGitUrl.indexOf("https://github.com/") + "https://github.com/".length();
            URL url = new URL("https://api.github.com/repos/" + originGitUrl.substring(start_at) + "/issues");

            logger.info(url.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            String encodedString = "Bearer " + gitApiKey;
            logger.info("auth {}", encodedString);
            con.setRequestProperty("Authorization", encodedString);
            con.setRequestProperty("Accept", "application/vnd.github+json");
            con.setDoOutput(true);

            Map<String, String> data = new HashMap<>();
            data.put("title", title);
            data.put("body", content);
            JSONObject json = new JSONObject(data);

            OutputStream os = con.getOutputStream();
            os.write(json.toString().getBytes());
            os.flush();
            os.close();

            logger.info("Git Message successfully sended");

            int responseCode = con.getResponseCode();

            InputStream in = null;
            if (responseCode >= 200 && responseCode < 300) {
                in = con.getInputStream();
            } else {
                in = con.getErrorStream();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuffer response = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            reader.close();

            logger.info("response message is {}", response.toString());
            JSONParser parser = new JSONParser();

            String issueURL = null;
            try {
                Object obj = parser.parse(response.toString());
                if (obj instanceof JSONObject) {
                    JSONObject jsons = (JSONObject) obj;
                    issueURL = jsons.get("url").toString();
                }
            }catch (ParseException e){
                throw new CustomException(ExceptionType.GITHUB_API_ERROR_RESPONSE);
            }
            logger.info("issue url : {}", issueURL);

            if(responseCode!=201){
                logger.warn("Error code {}", response);
                throw new CustomException(ExceptionType.GITHUB_API_ERROR_RESPONSE);
            }
            return issueURL;
        }catch (IOException e){
            e.printStackTrace();
            logger.warn("IOExcetion {}", "error occurs while getting result from toss server");
            throw new CustomException(ExceptionType.GITHUB_API_IO_ERROR);
        }
    }
}
