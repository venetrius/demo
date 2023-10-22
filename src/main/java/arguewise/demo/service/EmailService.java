package arguewise.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import okhttp3.*;
import java.io.IOException;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String emailUsername;

    @Value("${spring.mail.password}")
    private String emailPassword;
    private static final OkHttpClient httpClient = new OkHttpClient();

    @Value("${spring.mail.sender}")
    private String senderEmailAddress;


    public Response sendSimpleMessage(String email, String userName) throws IOException {
        String url = "https://api.mailgun.net/v3/" + emailUsername + "/messages";
        String credentials = Credentials.basic("api", emailPassword);

        RequestBody formBody = new FormBody.Builder()
                .add("from", senderEmailAddress)
                .add("to", email)
                .add("subject", "welcome on board!")
                .add("text", "Hi " + userName + ", welcome on board at ArgueWise!")
                .build();
        System.out.println("Sending email to: " + email);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", credentials)
                .post(formBody)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            System.out.println("Response from email service: " + response.body().string());
            return response;
        }
    }
}
