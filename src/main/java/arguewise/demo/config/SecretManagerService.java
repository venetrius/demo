package arguewise.demo.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class SecretManagerService {

    private final SecretsManagerClient secretsManagerClient;

    public SecretManagerService() {
        this.secretsManagerClient = SecretsManagerClient.builder()
                .region(Region.of("eu-north-1"))
                .build();
    }

    public Map<String, String> getSecretValue(String secretName) {
        GetSecretValueRequest request = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse response = secretsManagerClient.getSecretValue(request);

        String secretString = response.secretString();
        try {
            System.out.println("secretString: " + secretString); // TODO remove
            return new ObjectMapper().readValue(secretString, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse secret JSON", e);
        }
    }
}
