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


    public Response sendSimpleMessage(String email, String userName) throws IOException {
        String url = "https://api.mailgun.net/v3/" + emailUsername + "/messages";
        String credentials = Credentials.basic("api", emailPassword);

        RequestBody formBody = new FormBody.Builder()
                .add("from", "someone@gmail.com")
                .add("to", email)
                .add("subject", "welcome on board!")
                .add("text", "Hi " + userName + ", welcome on board at ArgueWise!")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", credentials)
                .post(formBody)
                .build();

        return httpClient.newCall(request).execute();
    }
}
