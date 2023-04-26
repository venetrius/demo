package arguewise.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService implements IEmailConfirmationSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendEmailConfirmation(String userEmail) {
        String topic = "email-confirmation";
        kafkaTemplate.send(topic, userEmail);
    }
}
