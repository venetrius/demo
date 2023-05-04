package arguewise.demo.integration;

import arguewise.demo.dto.Discussion.CreateDiscussionDTO;
import arguewise.demo.dto.Discussion.DiscussionResponseDTO;
import arguewise.demo.dto.userDiscussion.JoinDiscussionDTO;
import arguewise.demo.integration.space.SpaceTestUtility;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.Space;
import arguewise.demo.model.User;
import arguewise.demo.model.UsersDiscussion;
import arguewise.demo.repository.DiscussionRepository;
import arguewise.demo.repository.SpaceRepository;
import arguewise.demo.repository.UserDiscussionRepository;
import arguewise.demo.repository.UserRepository;
import com.github.javafaker.Faker;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UsersDiscussionControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDiscussionRepository userDiscussionRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private AuthTestUtil authTestUtil;

    private Faker faker;

    private String email;

    private HttpHeaders headers;

    private static final String BASE_URL = "http://localhost:";
    private String getUserDiscussionUrl(){
        return BASE_URL + port + "/api/me/discussions";
    }

    @BeforeEach
    public void setup() {
        faker = new Faker();
        email = faker.internet().emailAddress();
        String jwtToken = authTestUtil.registerAndLogin(email, "testPassword", email, port);
        headers = authTestUtil.createAuthorizationHeader(jwtToken);
    }

    @Test
    public void testJoinDiscussion() {
        Space space = SpaceTestUtility.createSpace(spaceRepository);
        User user = userRepository.findByEmail(email).orElseThrow();

        Discussion discussion = getDiscussion(space, user);

        JoinDiscussionDTO joinDiscussionDTO = new JoinDiscussionDTO();
        joinDiscussionDTO.setSide(UsersDiscussion.Side.PRO);

        HttpEntity<JoinDiscussionDTO> requestEntity = new HttpEntity<>(joinDiscussionDTO, headers);
        ResponseEntity<Void> response = restTemplate.exchange(getUserDiscussionUrl() + "/" + discussion.getId() + "/join", HttpMethod.POST, requestEntity, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(userDiscussionRepository.findByUserIdAndDiscussionId(user.getId(), discussion.getId())).isNotEmpty();
    }

    @NotNull
    private Discussion getDiscussion(Space space, User user) {
        Discussion discussion;
        CreateDiscussionDTO createDiscussionDTO = new CreateDiscussionDTO();
        createDiscussionDTO.setSpaceID(space.getId());
        createDiscussionDTO.setTopic(faker.lorem().sentence(3));
        createDiscussionDTO.setTimeLimit(LocalDateTime.now().plus(1, ChronoUnit.WEEKS));
        discussion = new Discussion(createDiscussionDTO, user, space);

        discussion = discussionRepository.save(discussion);
        return discussion;
    }

    @Test
    public void testJoinDiscussionWithSameSide() {
        Space space = SpaceTestUtility.createSpace(spaceRepository);
        User user = userRepository.findByEmail(email).orElseThrow();

        Discussion discussion = getDiscussion(space, user);

        JoinDiscussionDTO joinDiscussionDTO = new JoinDiscussionDTO();
        joinDiscussionDTO.setSide(UsersDiscussion.Side.PRO);

        // First join attempt
        HttpEntity<JoinDiscussionDTO> requestEntity = new HttpEntity<>(joinDiscussionDTO, headers);
        ResponseEntity<Void> response = restTemplate.exchange(getUserDiscussionUrl() + "/" + discussion.getId() + "/join", HttpMethod.POST, requestEntity, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Second join attempt with the same side
        response = restTemplate.exchange(getUserDiscussionUrl() + "/" + discussion.getId() + "/join", HttpMethod.POST, requestEntity, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void testJoinDiscussionWithDifferentSide() {
        Space space = SpaceTestUtility.createSpace(spaceRepository);
        User user = userRepository.findByEmail(email).orElseThrow();

        Discussion discussion = getDiscussion(space, user);

        JoinDiscussionDTO joinDiscussionDTO = new JoinDiscussionDTO();
        joinDiscussionDTO.setSide(UsersDiscussion.Side.PRO);

        // First join attempt
        HttpEntity<JoinDiscussionDTO> requestEntity = new HttpEntity<>(joinDiscussionDTO, headers);
        ResponseEntity<Void> response = restTemplate.exchange(getUserDiscussionUrl() + "/" + discussion.getId() + "/join", HttpMethod.POST, requestEntity, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Second join attempt with a different side
        joinDiscussionDTO.setSide(UsersDiscussion.Side.CONTRA);
        requestEntity = new HttpEntity<>(joinDiscussionDTO, headers);
        response = restTemplate.exchange(getUserDiscussionUrl() + "/" + discussion.getId() + "/join", HttpMethod.POST, requestEntity, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    public void testGetUserDiscussions() {
        // Create two spaces
        Space space1 = SpaceTestUtility.createSpace(spaceRepository);
        Space space2 = SpaceTestUtility.createSpace(spaceRepository);

        User user = userRepository.findByEmail(email).orElseThrow();

        // Create two discussions
        Discussion discussion1 = getDiscussion(space1, user);
        Discussion discussion2 = getDiscussion(space2, user);

        // Join both discussions
        joinDiscussion(discussion1.getId(), UsersDiscussion.Side.PRO);
        joinDiscussion(discussion2.getId(), UsersDiscussion.Side.CONTRA);

        // Get the list of joined discussions
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<DiscussionResponseDTO>> response = restTemplate.exchange(getUserDiscussionUrl(), HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<DiscussionResponseDTO>>() {});

        // Check the response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Extract the discussion IDs from the response
        List<Long> discussionIds = response.getBody().stream().map(DiscussionResponseDTO::getId).collect(Collectors.toList());

        // Check if both discussions are present in the response
        assertThat(discussionIds).contains(discussion1.getId(), discussion2.getId());
    }

    private void joinDiscussion(Long discussionId, UsersDiscussion.Side side) {
        JoinDiscussionDTO joinDiscussionDTO = new JoinDiscussionDTO();
        joinDiscussionDTO.setSide(side);

        HttpEntity<JoinDiscussionDTO> requestEntity = new HttpEntity<>(joinDiscussionDTO, headers);
        restTemplate.exchange(getUserDiscussionUrl() + "/" + discussionId + "/join", HttpMethod.POST, requestEntity, Void.class);
    }

}
