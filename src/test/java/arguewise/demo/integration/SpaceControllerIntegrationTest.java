package arguewise.demo.integration;

import arguewise.demo.model.Space;
import arguewise.demo.repository.SpaceRepository;
import org.junit.jupiter.api.AfterEach;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpaceControllerIntegrationTest {

    private static final String BASE_URL = "http://localhost:";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SpaceRepository spaceRepository;

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

    // @AfterEach
    // public void tearDown() {
    //     spaceRepository.deleteAll();
    // }

    @Test
    public void testCreateSpace() {

        Space space = new Space(null, "Technology", "A space for discussing technology-related topics.");
        HttpEntity<Space> request = new HttpEntity<>(space, headers);

        ResponseEntity<Space> responseEntity = restTemplate.postForEntity(getSpacesURL(), request, Space.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getId()).isNotNull();
        assertThat(responseEntity.getBody().getName()).isEqualTo(space.getName());
        assertThat(responseEntity.getBody().getDescription()).isEqualTo(space.getDescription());
    }

    @Test
    public void testUpdateSpace() {
        Space space = new Space(null, "Technology", "A space for discussing technology-related topics.");
        space = spaceRepository.save(space);

        Space updatedSpace = new Space(space.getId(), "Science", "A space for discussing science-related topics and discoveries.");
        HttpEntity<Space> request = new HttpEntity<>(updatedSpace, headers);

        restTemplate.put(getSpacesURL() + "/" + space.getId(), request);

        Space retrievedSpace = spaceRepository.findById(space.getId()).orElse(null);

        assertThat(retrievedSpace).isNotNull();
        assertThat(retrievedSpace.getName()).isEqualTo(updatedSpace.getName());
        assertThat(retrievedSpace.getDescription()).isEqualTo(updatedSpace.getDescription());
    }

    @Test
    public void testGetSpaces() {
        Space space1 = new Space(null, "Technology", "A space for discussing technology-related topics.");
        Space space2 = new Space(null, "Science", "A space for discussing science-related topics and discoveries.");
        spaceRepository.save(space1);
        spaceRepository.save(space2);

        HttpEntity<String> request = new HttpEntity<>("parameters", headers);

        ResponseEntity<List> responseEntity = restTemplate.exchange(getSpacesURL(), HttpMethod.GET, request, List.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().size()).isGreaterThan(1);
    }

    @Test
    public void testGetSpaceById() {
        Space space = new Space(null, "Technology", "A space for discussing technology-related topics.");
        space = spaceRepository.save(space);

        HttpEntity<String> request = new HttpEntity<>("parameters", headers);

        ResponseEntity<Space> responseEntity = restTemplate.exchange(getSpacesURL() + "/" + space.getId(), HttpMethod.GET, request, Space.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getId()).isEqualTo(space.getId());
        assertThat(responseEntity.getBody().getName()).isEqualTo(space.getName());
        assertThat(responseEntity.getBody().getDescription()).isEqualTo(space.getDescription());
    }

    @Test
    public void testDeleteSpace() {
        Space space = new Space(null, "Technology", "A space for discussing technology-related topics.");
        space = spaceRepository.save(space);

        HttpEntity<String> request = new HttpEntity<>("parameters", headers);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(getSpacesURL() + "/" + space.getId(), HttpMethod.DELETE, request, Void.class);

        assertThat(spaceRepository.findById(space.getId())).isEmpty();

    }
}
