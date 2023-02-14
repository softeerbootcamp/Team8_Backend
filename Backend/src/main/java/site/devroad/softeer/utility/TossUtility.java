package site.devroad.softeer.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

@Component
public class TossUtility {
    public static Logger logger = LoggerFactory.getLogger(TossUtility.class);
    @Value("${toss.apiKey}")
    private String tossApiKey;
    public void validateTossParams(String orderId, String paymentKey, Integer amount) throws CustomException {
        try {
            URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            String encodedString = "Basic " + tossApiKey;


            con.setRequestProperty("Authorization", encodedString);
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            String payload = String.format("{\"paymentKey\":\"%s\",\"amount\":%d,\"orderId\":\"%s\"}", paymentKey, amount, orderId);
            OutputStream os = con.getOutputStream();
            os.write(payload.getBytes());
            os.flush();
            os.close();

            int responseCode = con.getResponseCode();

            logger.info("encoded api key {}", encodedString);
            logger.info("payload is {}" , payload);
            logger.info("response code is {}", responseCode);

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

            if(responseCode!=200){
                throw new CustomException(ExceptionType.TOSS_ALREADY_PURCHASED);
            }
        }catch (IOException e){
            e.printStackTrace();
            logger.warn("IOExcetion {}", "error occurs while getting result from toss server");
            throw new CustomException(ExceptionType.TOSS_PURCHASE_FAILED);
        }
    }
}
