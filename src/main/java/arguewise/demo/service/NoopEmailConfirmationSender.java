package arguewise.demo.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class NoopEmailConfirmationSender implements IEmailConfirmationSender {
    @Override
    public void sendEmailConfirmation(String email, String username) {
        // Do nothing
    }
}
