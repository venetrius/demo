package arguewise.demo.integration.config;

import arguewise.demo.service.IEmailConfirmationSender;
import arguewise.demo.service.NoopEmailConfirmationSender;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;


@TestConfiguration
public class EmailTestConfiguration {
    @Bean
    @Profile("test")
    public IEmailConfirmationSender emailConfirmationSender() {
        return new NoopEmailConfirmationSender();
    }
}
