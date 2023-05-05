package arguewise.demo.integration;

import arguewise.demo.integration.util.AuthTestUtil;
import arguewise.demo.integration.util.space.SpaceTestUtility;
import arguewise.demo.model.Space;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SpaceControllerIntegrationTest {

    private static final String BASE_URL = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SpaceTestUtility spaceTestUtility;

    @Autowired
    private AuthTestUtil authTestUtil;

    private HttpHeaders headers;


    private String getSpacesURL(){
        return BASE_URL + port + "/api/spaces";
    }

    @BeforeEach
    public void setUp() {
        long random = (long) (Math.random() * 10000);
        String jwtToken = authTestUtil.registerAndLogin("test@example.com+"+ random, "testpassword", "test@example.com+"+random, port);
        headers = authTestUtil.createAuthorizationHeader(jwtToken);
    }

    @Test
    public void testCreateSpace() {

        Space space = new Space("Technology", "A space for discussing technology-related topics.");
        HttpEntity<Space> request = new HttpEntity<>(space, headers);

        ResponseEntity<Space> responseEntity = restTemplate.postForEntity(getSpacesURL(), request, Space.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getId()).isNotNull();
        assertThat(responseEntity.getBody().getName()).isEqualTo(space.getName());
        assertThat(responseEntity.getBody().getDescription()).isEqualTo(space.getDescription());
    }

    @Test
    public void testUpdateSpace() {
        Space space = spaceTestUtility.createSpace("Technology", "A space for discussing technology-related topics.");

        Space updatedSpace = new Space("Science", "A space for discussing science-related topics and discoveries.");
        HttpEntity<Space> request = new HttpEntity<>(updatedSpace, headers);

        restTemplate.put(getSpacesURL() + "/" + space.getId(), request);

        Space retrievedSpace = spaceTestUtility.findById(space.getId());

        assertThat(retrievedSpace).isNotNull();
        assertThat(retrievedSpace.getName()).isEqualTo(updatedSpace.getName());
        assertThat(retrievedSpace.getDescription()).isEqualTo(updatedSpace.getDescription());
    }

    @Test
    public void testGetSpaces() {
        Space space1 = spaceTestUtility.createSpace( "Technology", "A space for discussing technology-related topics.");
        Space space2 = spaceTestUtility.createSpace("Science", "A space for discussing science-related topics and discoveries.");

        HttpEntity<String> request = new HttpEntity<>(null, headers);

        ResponseEntity<List> responseEntity = restTemplate.exchange(getSpacesURL(), HttpMethod.GET, request, List.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().size()).isGreaterThan(1);
    }

    @Test
    public void testGetSpaceById() {
        Space space = spaceTestUtility.createSpace("Technology", "A space for discussing technology-related topics.");

        HttpEntity<String> request = new HttpEntity<>(null, headers);

        ResponseEntity<Space> responseEntity = restTemplate.exchange(getSpacesURL() + "/" + space.getId(), HttpMethod.GET, request, Space.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getId()).isEqualTo(space.getId());
        assertThat(responseEntity.getBody().getName()).isEqualTo(space.getName());
        assertThat(responseEntity.getBody().getDescription()).isEqualTo(space.getDescription());
    }

    @Test
    public void testDeleteSpace() {
        Space space = spaceTestUtility.createSpace("Technology", "A space for discussing technology-related topics.");

        HttpEntity<String> request = new HttpEntity<>(null, headers);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(getSpacesURL() + "/" + space.getId(), HttpMethod.DELETE, request, Void.class);

        assertThat(spaceTestUtility.findById(space.getId())).isNull();

    }
}
