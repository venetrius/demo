package arguewise.demo.integration;

import arguewise.demo.dto.argument.ArgumentResponseDTO;
import arguewise.demo.dto.argument.CreateArgumentDTO;
import arguewise.demo.dto.argument.UpdateArgumentDTO;
import arguewise.demo.integration.util.AuthTestUtil;
import arguewise.demo.integration.util.DiscussionTestUtility;
import arguewise.demo.integration.util.space.SpaceTestUtility;
import arguewise.demo.model.Argument;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.Space;
import arguewise.demo.model.UsersDiscussion;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ArgumentControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ArgumentTestUtil argumentTestUtil;

    @Autowired
    private SpaceTestUtility spaceTestUtility;

    @Autowired
    private DiscussionTestUtility discussionTestUtility;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AuthTestUtil authTestUtil;

    private HttpHeaders headers;

    private Faker faker;

    private String email;

    private static final String BASE_URL = "http://localhost:";
    private String getDiscussionsArgumentsUrl(Long discussionId) {
        return BASE_URL + port + "/api/discussions/" + discussionId + "/arguments";
    }

    private String getArgumentsUrl() {
        return BASE_URL + port + "/api/arguments";
    }

    private String getDiscussionUrl(){
        return BASE_URL + port + "/api/discussions";
    }

    @BeforeEach
    public void setup() {
        faker = new Faker();
        email = faker.internet().emailAddress();
        String jwtToken = authTestUtil.registerAndLogin(email, "testPassword", email, port);
        headers = authTestUtil.createAuthorizationHeader(jwtToken);
    }

    private ArgumentResponseDTO createArgument() {
        return argumentTestUtil.createArgument(email, headers, port);
    }
    private ArgumentResponseDTO createArgument(Long discussionId) {
        return argumentTestUtil.createArgument(email, headers, port, discussionId);
    }

    @Test
    public void testCreateArgument() {
        Space space = spaceTestUtility.createSpace();
        Discussion discussion = discussionTestUtility.createDiscussion(space, authTestUtil.getUserByEmail(email));
        CreateArgumentDTO createArgumentDTO = new CreateArgumentDTO();
        createArgumentDTO.setDetails(argumentTestUtil.createArgumentDetailsList());
        createArgumentDTO.setTitle("This is the title");

        UsersDiscussion.Side side = UsersDiscussion.Side.PRO;
        discussionTestUtility.joinDiscussion(discussion.getId(), side, headers, port);

        HttpEntity<CreateArgumentDTO> requestEntity = new HttpEntity<>(createArgumentDTO, headers);
        ResponseEntity<ArgumentResponseDTO> response = restTemplate.exchange(getDiscussionsArgumentsUrl(discussion.getId()), HttpMethod.POST, requestEntity, ArgumentResponseDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getArgumentDetails()).isEqualTo(createArgumentDTO.getDetails());
    }

    @Test
    public void testGetArgumentsByDiscussion() {
        ArgumentResponseDTO argument1 = createArgument();
        ArgumentResponseDTO argument2 = createArgument(argument1.getDiscussionID());

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<ArgumentResponseDTO[]> response = restTemplate.exchange(getDiscussionsArgumentsUrl(argument1.getDiscussionID()), HttpMethod.GET, requestEntity, ArgumentResponseDTO[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void testGetArgumentById() {
        ArgumentResponseDTO argumentResponseDTO = createArgument();
        ResponseEntity<ArgumentResponseDTO> response = argumentTestUtil.getArgumentById(argumentResponseDTO.getId(), headers, getArgumentsUrl());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getArgumentDetails()).isEqualTo(argumentResponseDTO.getArgumentDetails());
    }

    @Test
    public void testUpdateArgument() {
        ArgumentResponseDTO argumentResponseDTO = createArgument();

        UpdateArgumentDTO updatedArgument = new UpdateArgumentDTO();
        updatedArgument.setArgumentDetails(argumentTestUtil.createArgumentDetailsList());
        updatedArgument.setTitle(faker.lorem().characters(20));

        HttpEntity<UpdateArgumentDTO> requestEntity = new HttpEntity<>(updatedArgument, headers);
        ResponseEntity<ArgumentResponseDTO> response = restTemplate.exchange(getArgumentsUrl() + "/" + argumentResponseDTO.getId(), HttpMethod.PUT, requestEntity, ArgumentResponseDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getArgumentDetails()).isEqualTo(updatedArgument.getArgumentDetails());
        assertThat(response.getBody().getTitle()).isEqualTo(updatedArgument.getTitle());
    }

    @Test
    public void testDeleteArgument() {
        ArgumentResponseDTO argumentResponseDTO = createArgument();

        HttpEntity<String> requestEntity = new HttpEntity<>("parameters", headers);
        ResponseEntity<Void> response = restTemplate.exchange(getArgumentsUrl() + "/" + argumentResponseDTO.getId(), HttpMethod.DELETE, requestEntity, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(argumentTestUtil.findById(argumentResponseDTO.getId())).isEmpty();
    }
}
