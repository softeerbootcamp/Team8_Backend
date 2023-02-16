package site.devroad.softeer.utility;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.*;

@Component
public class GithubUtility {

    Logger logger = LoggerFactory.getLogger(GithubUtility.class);

    @Value("${github.apiKey}")
    private String gitApiKey;

    @Value("${github.apiKey}")
    private String token;

    private static Map<String, String> extensionMap = new HashMap<>(){{
        put("Java", ".java");
        put("Python", ".py");
        put("Go", ".go");
        put("C", ".c");
        put("Cpp", ".cpp");
    }};

    public String getMainExtensionFromRepo(String owner, String repo) {
        try {
            String path = "https://api.github.com/repos/" + owner + "/" + repo;
            URL url = new URL(path);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + token);
            con.setRequestProperty("Accept", "application/vnd.github.v3+json");

            int status = con.getResponseCode();
            if (status != 200) {
                throw new RuntimeException("Failed to get repository contents: " + status);
            }

            InputStream in = con.getInputStream();
            byte[] responseBytes = in.readAllBytes();
            String response = new String(responseBytes);

            JSONObject jsonObject = new JSONObject(response);

            String language = jsonObject.getString("language");
            if (!extensionMap.containsKey(language))
                return ".java";
            return extensionMap.get(language);
        }catch (IOException e){
            e.printStackTrace();
            throw new CustomException(ExceptionType.GITHUB_API_ERROR_RESPONSE);
        }
    }

    public Map<String, String> getAllCodeFromRepo(String owner, String repo, String fileType) {
        try {
            String path = "https://api.github.com/repos/" + owner + "/" + repo + "/contents";
            URL url = new URL(path);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + token);
            con.setRequestProperty("Accept", "application/vnd.github.v3+json");

            int status = con.getResponseCode();
            if (status != 200) {
                throw new RuntimeException("Failed to get repository contents: " + status);
            }

            InputStream in = con.getInputStream();
            byte[] responseBytes = in.readAllBytes();
            String response = new String(responseBytes);

            JSONArray jsonArray = new JSONArray(response);

            logger.info("jsonArray {}", jsonArray);
            Map<String, String> javaFiles = new HashMap<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                String name = (String) obj.get("name");
                String type = (String) obj.get("type");
                String pathUrl = (String) obj.get("url");
                if (type.equals("dir")) {
                    javaFiles.putAll(getFilesFromDirectory(pathUrl, fileType));
                } else if (name.endsWith(fileType)) {
                    String downloadUrl = (String) obj.get("download_url");
                    String content = getFileContentFromUrl(downloadUrl);
                    javaFiles.put(getFileName(pathUrl), content);
                }
            }

            return javaFiles;
        }catch (IOException e){
            e.printStackTrace();
            throw new CustomException(ExceptionType.GITHUB_API_ERROR_RESPONSE);
        }
    }

    //create new Issue from originGitUrl and returns new Issue url;
    public String createIssue(String owner, String repo, String title, String content){
        logger.info("createIssue {}, {}, {}", owner+"/"+repo, title, content);

        try {
            URL url = new URL("https://api.github.com/repos/" + owner+"/"+repo + "/issues");

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
            JSONObject jsons = new JSONObject(response.toString());
            String issueURL = jsons.get("html_url").toString();

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

    private  String getFileName(String originalUrl) {
        originalUrl = URLDecoder.decode(originalUrl, Charset.defaultCharset());
        originalUrl = originalUrl.replace("?ref=main", "");
        return originalUrl.substring(originalUrl.indexOf("contents")+"contents".length());
    }

    private Map<String, String> getFilesFromDirectory(String urlStr, String fileType) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + token);
        con.setRequestProperty("Accept", "application/vnd.github.v3+json");

        int status = con.getResponseCode();
        if (status != 200) {
            throw new RuntimeException("Failed to get directory contents: " + status);
        }

        InputStream in = con.getInputStream();
        byte[] responseBytes = in.readAllBytes();
        String response = new String(responseBytes);

        JSONArray jsonArray = new JSONArray(response.toString());
        Map<String, String> javaFiles = new HashMap<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);
            String name = (String) obj.get("name");
            String type = (String) obj.get("type");
            String pathUrl = (String) obj.get("url");
            if (type.equals("dir")) {
                javaFiles.putAll(getFilesFromDirectory(pathUrl, fileType));
            } else if (name.endsWith(fileType)) {
                String downloadUrl = (String) obj.get("download_url");
                String content = getFileContentFromUrl(downloadUrl);
                logger.info("download_url : {}", downloadUrl);
                javaFiles.put(getFileName(pathUrl), content);
            }
        }

        return javaFiles;
    }

    private String getFileContentFromUrl(String downloadUrl) throws IOException {
        logger.info("get file content from {",downloadUrl);

        URL url = new URL(downloadUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + token);
        con.setRequestProperty("Accept", "application/vnd.github.v3.raw");

        int status = con.getResponseCode();
        if (status != 200) {
            throw new RuntimeException("Failed to get file content: " + status);
        }

        InputStream in = con.getInputStream();
        byte[] responseBytes = in.readAllBytes();
        String content = new String(responseBytes);

        // Decode Base64-encoded files
        if (content.contains("base64")) {
            content = content.split(",")[1];
            content = new String(Base64.getDecoder().decode(content));
        }

        return content;
    }



}
