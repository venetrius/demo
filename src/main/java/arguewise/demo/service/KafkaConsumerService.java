package arguewise.demo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService implements IEmailConfirmationListener {


    @KafkaListener(topics = "email-confirmation", groupId = "email-group")
    @Override
    public void onEmailConfirmation(String userEmail) {
        sendConfirmationEmail(userEmail);
    }

    private void sendConfirmationEmail(String userEmail) {
        System.out.println("Sending confirmation email to " + userEmail);
        // TODO:
        // Use MailGun's API to send the confirmation email.
        // Implement your logic here.
    }
}
