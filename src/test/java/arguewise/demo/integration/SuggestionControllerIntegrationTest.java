package arguewise.demo.integration;

import arguewise.demo.dto.argument.ArgumentResponseDTO;
import arguewise.demo.dto.suggestion.CreateSuggestionDTO;
import arguewise.demo.dto.suggestion.SuggestionResponseDTO;
import arguewise.demo.integration.util.AuthTestUtil;
import arguewise.demo.model.Argument;
import arguewise.demo.model.Suggestion;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SuggestionControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ArgumentTestUtil argumentTestUtil;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AuthTestUtil authTestUtil;

    private HttpHeaders headers;

    private Faker faker;

    private String email;

    private static final String BASE_URL = "http://localhost:";

    private String getSuggestionsUrl() {
        return BASE_URL + port + "/api/suggestions";
    }

    @BeforeEach
    public void setup() {
        faker = new Faker();
        email = faker.internet().emailAddress();
        String jwtToken = authTestUtil.registerAndLogin(email, "testPassword", email, port);
        headers = authTestUtil.createAuthorizationHeader(jwtToken);
    }

    private SuggestionResponseDTO createSuggestion(Argument argument) {
        CreateSuggestionDTO dto = new CreateSuggestionDTO();
        dto.setArgumentId(argument.getId());
        dto.setType(Suggestion.SuggestionType.ADDITION);
        dto.setSection("Section");
        dto.setPosition(1);
        dto.setText("Suggestion text");
        dto.setComment("Suggestion comment");

        HttpEntity<CreateSuggestionDTO> requestEntity = new HttpEntity<>(dto, headers);
        ResponseEntity<SuggestionResponseDTO> response = restTemplate.exchange(getSuggestionsUrl(), HttpMethod.POST, requestEntity, SuggestionResponseDTO.class);
        return response.getBody();
    }

    @Test
    public void testCreateSuggestion() {
        ArgumentResponseDTO argumentResponseDTO = argumentTestUtil.createArgument(email, headers, port);
        Argument argument = argumentTestUtil.findById(argumentResponseDTO.getId()).get();

        SuggestionResponseDTO suggestion = createSuggestion(argument);

        assertThat(suggestion).isNotNull();
        assertThat(suggestion.getArgumentId()).isEqualTo(argument.getId());
    }
}
