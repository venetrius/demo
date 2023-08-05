package arguewise.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaConsumerService implements IEmailConfirmationListener {

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "email-group", groupId = "email-group")
    @Override
    public void onEmailConfirmation(String userEmail, String userName) {
        System.out.println("received message to create email confirmation: " + userEmail + " " + userName);
        sendConfirmationEmail(userEmail, userName);
    }

    private void sendConfirmationEmail(String userEmail, String userName) {
        try {
            emailService.sendSimpleMessage(userEmail, userName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
