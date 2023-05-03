package arguewise.demo.integration;

import arguewise.demo.dto.Discussion.CreateDiscussionDTO;
import arguewise.demo.dto.Discussion.DiscussionResponseDTO;
import arguewise.demo.dto.Discussion.UpdateDiscussionDTO;
import arguewise.demo.integration.space.SpaceTestUtility;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.Space;
import arguewise.demo.repository.DiscussionRepository;
import arguewise.demo.repository.SpaceRepository;
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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DiscussionControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private AuthTestUtil authTestUtil;

    @Autowired
    private SpaceRepository spaceRepository;
    private HttpHeaders headers;

    private Faker faker;

    private static final String BASE_URL = "http://localhost:";
    private String getDiscussionUrl(){
        return BASE_URL + port + "/api/discussions";
    }


    @BeforeEach
    public void setup() {
        faker = new Faker();
        String email = faker.internet().emailAddress();
        String jwtToken = authTestUtil.registerAndLogin(email, "testPassword", email, port);
        headers = authTestUtil.createAuthorizationHeader(jwtToken);
    }

    private DiscussionResponseDTO createDiscussion() {
        Space space = SpaceTestUtility.createSpace(spaceRepository);

        CreateDiscussionDTO createDiscussionDTO = new CreateDiscussionDTO();
        createDiscussionDTO.setSpaceID(space.getId());
        createDiscussionDTO.setTopic(faker.lorem().sentence(3));
        createDiscussionDTO.setTimeLimit(LocalDateTime.now().plus(1, ChronoUnit.WEEKS));

        HttpEntity<CreateDiscussionDTO> requestEntity = new HttpEntity<>(createDiscussionDTO, headers);
        ResponseEntity<DiscussionResponseDTO> response = restTemplate.exchange(getDiscussionUrl(), HttpMethod.POST, requestEntity, DiscussionResponseDTO.class);

        return response.getBody();
    }

    @Test
    public void testGetAllDiscussions() {
        DiscussionResponseDTO discussionResponseDTO1 = createDiscussion();
        DiscussionResponseDTO discussionResponseDTO2 = createDiscussion();

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<Discussion[]> response = restTemplate.exchange(getDiscussionUrl(), HttpMethod.GET, requestEntity, Discussion[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void testGetDiscussionById() {
        DiscussionResponseDTO discussion = createDiscussion();
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<Discussion> response = restTemplate.exchange(getDiscussionUrl() + "/" + discussion.getId(), HttpMethod.GET, requestEntity, Discussion.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getTopic()).isEqualTo(discussion.getTopic());
    }

    @Test
    public void testCreateDiscussion() {
        Space space = new Space();
        space.setDescription("This is a test");
        space.setName("some name");
        spaceRepository.save(space);
        CreateDiscussionDTO createDiscussionDTO = new CreateDiscussionDTO();
        createDiscussionDTO.setSpaceID(space.getId());
        createDiscussionDTO.setTopic(faker.lorem().sentence(3));
        createDiscussionDTO.setTimeLimit(LocalDateTime.now().plus(1, ChronoUnit.WEEKS));

        HttpEntity<CreateDiscussionDTO> requestEntity = new HttpEntity<>(createDiscussionDTO, headers);

        ResponseEntity<DiscussionResponseDTO> response = restTemplate.exchange(getDiscussionUrl(), HttpMethod.POST, requestEntity, DiscussionResponseDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getTopic()).isEqualTo(createDiscussionDTO.getTopic());
        assertThat(response.getBody().getSpaceID()).isEqualTo(space.getId());
    }

    @Test
    public void testUpdateDiscussion() {
        DiscussionResponseDTO discussion = createDiscussion();
        UpdateDiscussionDTO updatedDiscussion = new UpdateDiscussionDTO();
        updatedDiscussion.setTopic("Updated Test Topic");

        HttpEntity<UpdateDiscussionDTO> requestEntity = new HttpEntity<>(updatedDiscussion, headers);
        ResponseEntity<DiscussionResponseDTO> response = restTemplate.exchange(getDiscussionUrl() + "/" + discussion.getId(), HttpMethod.PUT, requestEntity, DiscussionResponseDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getTopic()).isEqualTo(updatedDiscussion.getTopic());
    }

    @Test
    public void testDeleteDiscussion() {
        DiscussionResponseDTO discussion = createDiscussion();
        HttpEntity<String> requestEntity = new HttpEntity<>("parameters", headers);
        ResponseEntity<Void> response = restTemplate.exchange(getDiscussionUrl() + "/" + discussion.getId(), HttpMethod.DELETE, requestEntity, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(discussionRepository.findById(discussion.getId())).isEmpty();
    }

}
