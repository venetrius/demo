package arguewise.demo.integration;

import arguewise.demo.dto.argument.ArgumentResponseDTO;
import arguewise.demo.dto.argument.CreateArgumentDTO;
import arguewise.demo.integration.util.AuthTestUtil;
import arguewise.demo.integration.util.DiscussionTestUtility;
import arguewise.demo.integration.util.space.SpaceTestUtility;
import arguewise.demo.model.Argument;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.Space;
import arguewise.demo.repository.ArgumentRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ArgumentTestUtil {
    @Autowired
    private ArgumentRepository argumentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SpaceTestUtility spaceTestUtility;

    @Autowired
    private DiscussionTestUtility discussionTestUtility;

    @Autowired
    private AuthTestUtil authTestUtil;

    private Faker faker = new Faker();

    private static final String BASE_URL = "http://localhost:";

    private String getDiscussionsArgumentsUrl(int port, Long discussionId) {
        return BASE_URL + port + "/api/discussions/" + discussionId + "/arguments";
    }

    public List<String> createArgumentDetailsList() {
        int numberOfParagraphs = 3;
        List<String> paragraphs = new ArrayList<>();
        for (int i = 0; i < numberOfParagraphs; i++) {
            paragraphs.add(faker.lorem().paragraph());
        }
        return paragraphs;
    }

    public Optional<Argument> findById(Long argumentId) {
        Optional<Argument> argumentOptional = argumentRepository.findById(argumentId);
        return argumentOptional;
    }

    public ResponseEntity<ArgumentResponseDTO> getArgumentById(Long argumentId, HttpHeaders headers, String argumentsUrl) {
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        return restTemplate.exchange(argumentsUrl + "/" + argumentId, HttpMethod.GET, requestEntity, ArgumentResponseDTO.class);
    }

    public ArgumentResponseDTO createArgument(String email, HttpHeaders headers, int port) {
        Space space = spaceTestUtility.createSpace();
        Discussion discussion = discussionTestUtility.createDiscussion(space, authTestUtil.getUserByEmail(email));
        CreateArgumentDTO createArgumentDTO = new CreateArgumentDTO();
        createArgumentDTO.setDetails(createArgumentDetailsList());
        createArgumentDTO.setTitle("This is the title");
        HttpEntity<CreateArgumentDTO> requestEntity = new HttpEntity<>(createArgumentDTO, headers);
        ResponseEntity<ArgumentResponseDTO> response = restTemplate.exchange(getDiscussionsArgumentsUrl(port, discussion.getId()), HttpMethod.POST, requestEntity, ArgumentResponseDTO.class);
        return response.getBody();
    }

    public ArgumentResponseDTO createArgument(String email, HttpHeaders headers, int port, Long discussionId) {
        CreateArgumentDTO createArgumentDTO = new CreateArgumentDTO();
        createArgumentDTO.setDetails(createArgumentDetailsList());
        createArgumentDTO.setTitle("This is the title");
        HttpEntity<CreateArgumentDTO> requestEntity = new HttpEntity<>(createArgumentDTO, headers);
        ResponseEntity<ArgumentResponseDTO> response = restTemplate.exchange(getDiscussionsArgumentsUrl(port, discussionId), HttpMethod.POST, requestEntity, ArgumentResponseDTO.class);
        return response.getBody();
    }
}
