package arguewise.demo.config.gson;

import arguewise.demo.batch.chatbot.LocalDateTimeTypeAdapter;
import com.google.gson.Gson;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class GsonConfiguration {

    @Bean
    public GsonBuilderCustomizer typeAdapterRegistration() {
        return builder -> {
            builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter());
        };
    }
}
