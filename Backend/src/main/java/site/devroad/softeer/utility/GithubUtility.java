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
import java.util.*;

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
                    issueURL = jsons.get("html_url").toString();
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

    public List<String> getPaths(String originRepoUrl){
        try {
            String repoURL = originRepoUrl.substring("https://github.com/".length());
            logger.info(repoURL);

            URL url = new URL("https://api.github.com/repos/"+repoURL+"/contents/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            String encodedString = "Bearer " + gitApiKey;
            logger.info("auth {}", encodedString);
            con.setRequestProperty("Authorization", encodedString);
            con.setRequestProperty("Accept", "application/vnd.github+json");
            con.setDoOutput(true);

            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            JSONParser parser = new JSONParser();
            String title = null;

            List<String> results = new ArrayList<>();
            try {
                Object obj = parser.parse(content.toString());
                if (obj instanceof List) {
                    for(Object map : (List)obj){
                        Map conv = (Map) map;
                        logger.info(conv.toString());
                        title = conv.get("path").toString();
                        results.add(title);
                    }
                }
                return results;
            }catch (ParseException e){
                throw new CustomException(ExceptionType.GITHUB_API_ERROR_RESPONSE);
            }
        }catch (IOException e){
            e.printStackTrace();
            logger.warn("IOExcetion {}", "error occurs while getting result from toss server");
            throw new CustomException(ExceptionType.GITHUB_API_IO_ERROR);
        }
    }

    //file path must not start with /
    public String getOneFile(String originRepoUrl, String filePath){
        try {
            String repoURL = originRepoUrl.substring("https://github.com/".length());
            logger.info(repoURL);

            URL url = new URL("https://api.github.com/repos/"+repoURL+"/contents/" + filePath);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            String encodedString = "Bearer " + gitApiKey;
            logger.info("auth {}", encodedString);
            con.setRequestProperty("Authorization", encodedString);
            con.setRequestProperty("Accept", "application/vnd.github+json");
            con.setDoOutput(true);

            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            JSONParser parser = new JSONParser();
            String base64data = null;
            try {
                Object obj = parser.parse(content.toString());
                if (obj instanceof JSONObject) {
                    JSONObject jsons = (JSONObject) obj;
                    base64data = jsons.get("content").toString().replace("\n", "");
                    byte[] decodedBytes = Base64.getDecoder().decode(base64data);
                    String decodedStr = new String(decodedBytes);
                    logger.info("data is {}", decodedStr);
                    return decodedStr;
                }
            }catch (ParseException e){
                throw new CustomException(ExceptionType.GITHUB_API_ERROR_RESPONSE);
            }
            throw new CustomException(ExceptionType.GITHUB_API_ERROR_RESPONSE);

        }catch (IOException e){
            e.printStackTrace();
            logger.warn("IOExcetion {}", "error occurs while getting result from toss server");
            throw new CustomException(ExceptionType.GITHUB_API_IO_ERROR);
        }
    }


}
