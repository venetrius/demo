package arguewise.demo.integration;

import arguewise.demo.dto.Discussion.DiscussionResponseDTO;
import arguewise.demo.integration.util.AuthTestUtil;
import arguewise.demo.integration.util.DiscussionTestUtility;
import arguewise.demo.integration.util.space.SpaceTestUtility;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.Space;
import arguewise.demo.model.User;
import arguewise.demo.model.UsersDiscussion;
import com.github.javafaker.Faker;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

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
    private DiscussionTestUtility discussionTestUtility;

    @Autowired
    private SpaceTestUtility spaceTestUtility;

    @Autowired
    private AuthTestUtil authTestUtil;

    private Faker faker;

    private String email;

    private HttpHeaders headers;

    private static final String BASE_URL = "http://localhost:";

    private String getUserDiscussionUrl() {
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
        Space space = spaceTestUtility.createSpace();
        User user = authTestUtil.getUserByEmail(email);
        Discussion discussion = getDiscussion(space, user);
        UsersDiscussion.Side side = UsersDiscussion.Side.PRO;

        ResponseEntity<Void> response = discussionTestUtility.joinDiscussion(getUserDiscussionUrl(), discussion.getId(), side, headers);;

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(discussionTestUtility.findUserDiscussionByUserIdAndDiscussionId(user.getId(), discussion.getId())).isNotEmpty();
    }

    @NotNull
    private Discussion getDiscussion(Space space, User user) {
        return discussionTestUtility.createDiscussion(space, user);
    }

    @Test
    public void testTryToJoinSameDiscussionTwiceWithSameSide() {
        Space space = spaceTestUtility.createSpace();
        User user = authTestUtil.getUserByEmail(email);
        Discussion discussion = getDiscussion(space, user);
        UsersDiscussion.Side side = UsersDiscussion.Side.PRO;

        // First join attempt
        ResponseEntity<Void> response = discussionTestUtility.joinDiscussion(getUserDiscussionUrl(), discussion.getId(), side, headers);;
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Second join attempt with the same side
        response = discussionTestUtility.joinDiscussion(getUserDiscussionUrl(), discussion.getId(), side, headers);;
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void testTryJoinDiscussionTwiceWithDifferentSide() {
        Space space = spaceTestUtility.createSpace();
        User user = authTestUtil.getUserByEmail(email);
        Discussion discussion = getDiscussion(space, user);
        UsersDiscussion.Side firstSide = UsersDiscussion.Side.PRO;
        UsersDiscussion.Side secondSide = UsersDiscussion.Side.CONTRA;

        // First join attempt
        ResponseEntity<Void> response = discussionTestUtility.joinDiscussion(getUserDiscussionUrl(), discussion.getId(), firstSide, headers);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Second join attempt with a different side
        response = discussionTestUtility.joinDiscussion(getUserDiscussionUrl(), discussion.getId(), secondSide, headers);;
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    public void testGetUserDiscussions() {
        // Create two spaces
        Space space1 = spaceTestUtility.createSpace();
        Space space2 = spaceTestUtility.createSpace();

        User user = authTestUtil.getUserByEmail(email); 

        // Create two discussions
        Discussion discussion1 = getDiscussion(space1, user);
        Discussion discussion2 = getDiscussion(space2, user);
        // Join both discussions
        discussionTestUtility.joinDiscussion(getUserDiscussionUrl(), discussion1.getId(), UsersDiscussion.Side.PRO, headers);
        discussionTestUtility.joinDiscussion(getUserDiscussionUrl(), discussion2.getId(), UsersDiscussion.Side.CONTRA, headers);

        // Get the list of joined discussions
        ResponseEntity<List<DiscussionResponseDTO>> response = discussionTestUtility.getUserDiscussions(getUserDiscussionUrl(), headers);

        // Check the response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Extract the discussion IDs from the response
        List<Long> discussionIds = response.getBody().stream().map(DiscussionResponseDTO::getId).collect(Collectors.toList());

        // Check if both discussions are present in the response
        assertThat(discussionIds).contains(discussion1.getId(), discussion2.getId());
    }
}