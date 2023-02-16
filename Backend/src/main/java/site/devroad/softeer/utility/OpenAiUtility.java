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
import java.util.HashMap;
import java.util.Map;

@Component
public class OpenAiUtility {

    private static Logger logger = LoggerFactory.getLogger(OpenAiUtility.class);
    @Value("${openAi.apiKey}")
    private String token;

    private HttpURLConnection getConnection(String urlStr, String httpMethod, Map<String, Object> jsonBody) throws CustomException {
        try{
            URL url = new URL(urlStr);
            logger.info("url is {} {}", url.toString(), token);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(httpMethod);
            con.setRequestProperty("Authorization", "Bearer " + token);
            con.setRequestProperty("Content-Type", "application/json");

            if(!httpMethod.equals("GET")){
                con.setDoOutput(true);
            }


            JSONObject json = new JSONObject(jsonBody);

            logger.info(json.toString());
            OutputStream os = con.getOutputStream();
            os.write(json.toString().getBytes());
            os.flush();
            os.close();

            return con;
        }catch(IOException e){
            e.printStackTrace();
            throw new CustomException(ExceptionType.CONNECTION_ESTABLISH_FAILED);
        }
    }

    public String getCodeSummary(String rawCode){
        try {
            //-d '{"model": "text-davinci-003", "prompt": "Say this is a test", "temperature": 0, "max_tokens": 7}'
            Map<String, Object> body = new HashMap<>();
            body.put("model", "text-davinci-003");
            body.put("prompt", rawCode + "what does this code does?");
            body.put("temperature", 0);
            body.put("max_tokens", 50);

            HttpURLConnection con = getConnection("https://api.openai.com/v1/completions", "POST", body);
            String response = getResponse(con);

            return getChoicesFromResponse(response);
        }catch (IOException e){
            logger.warn(e.getMessage());
            return null;
        }
    }


    public String getCodeReview(String rawCode){

        try {
            Map<String, Object> body = new HashMap<>();
            body.put("model", "text-davinci-003");
            body.put("prompt", rawCode + "\n\n you did a good job but there is 5 suggestion for improving your code: \n");
            body.put("temperature", 0);
            body.put("max_tokens", 150);

            HttpURLConnection con = getConnection("https://api.openai.com/v1/completions", "POST", body);
            String response = getResponse(con);

            return getChoicesFromResponse(response);
        }catch (IOException e){
            logger.warn(e.getMessage());
            return null;
        }
    }


    private String getResponse(HttpURLConnection con) throws IOException {
        int responseCode = con.getResponseCode();
        logger.info("Response code {}", responseCode);
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
        return response.toString();
    }


    private String getChoicesFromResponse(String jsonString){
        JSONObject json = new JSONObject(jsonString);
        JSONArray choices = json.getJSONArray("choices");
        String text = choices.getJSONObject(0).getString("text");
        return text;

    }
}

