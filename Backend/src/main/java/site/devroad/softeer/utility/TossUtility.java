package site.devroad.softeer.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.devroad.softeer.exceptions.CustomException;
import site.devroad.softeer.exceptions.ExceptionType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
            String encodedString = "Basic " + Base64.getEncoder().encodeToString(tossApiKey.getBytes());

            con.setRequestProperty("Authorization", encodedString);
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            String payload = String.format("{\"paymentKey\":\"%s\",\"amount\":%d,\"orderId\":\"%s\"}", paymentKey, amount, orderId);
            OutputStream os = con.getOutputStream();
            os.write(payload.getBytes());
            os.flush();
            os.close();

            int responseCode = con.getResponseCode();
            logger.info("payload is {}" , payload);
            logger.info("response code is {}", responseCode);

            if(responseCode == 401){
                return;
            }

            if(responseCode!=200){
                throw new CustomException(ExceptionType.TOSS_PURCHASE_FAILED);
            }


            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        }catch (IOException e){
            e.printStackTrace();
            logger.warn("IOExcetion {}", "error occurs while getting result from toss server");
            throw new CustomException(ExceptionType.TOSS_PURCHASE_FAILED);
        }
    }
}
