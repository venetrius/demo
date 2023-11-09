package arguewise.demo.config.gson;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GsonConfiguration {

    @Bean
    public Gson gson() {
        return new Gson();
    }
}
