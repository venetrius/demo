package arguewise.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SecretsConfiguration {

    @Autowired
    private SecretManagerService secretsService;

    @Autowired
    private ConfigurableEnvironment environment;

    @PostConstruct
    public void init() {
        try {
            Map<String, String> secrets = secretsService.getSecretValue("POC_secret");
            Map<String, Object> secretsObjectMap = secrets.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> (Object) e.getValue()));

            MutablePropertySources propertySources = environment.getPropertySources();
            propertySources.addFirst(new MapPropertySource("secretsManagerProperties", secretsObjectMap));
        } catch (Exception e) {
            System.out.println("Failed to load secrets from AWS Secrets Manager");
        }
    }
}
