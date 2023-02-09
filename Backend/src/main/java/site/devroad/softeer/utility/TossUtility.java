package site.devroad.softeer.utility;

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
@Component
public class TossUtility {
    @Value("${toss.apiKey}")
    private String tossApiKey;
    public void validateTossParams(String orderId, String paymentKey, Integer amount) throws CustomException {
        try {
            URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Basic " + tossApiKey);
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            String payload = String.format("{\"paymentKey\":\"%s\",\"amount\":%d,\"orderId\":\"%s\"}", paymentKey, amount, orderId);
            OutputStream os = con.getOutputStream();
            os.write(payload.getBytes());
            os.flush();
            os.close();

            int responseCode = con.getResponseCode();
            System.out.println("Response Code: " + responseCode);
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

            System.out.println(response.toString());
        }catch (IOException e){
            throw new CustomException(ExceptionType.TOSS_PURCHASE_FAILED);
        }
    }
}
