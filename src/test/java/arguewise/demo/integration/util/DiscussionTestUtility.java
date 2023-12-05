package arguewise.demo.integration.util;

import arguewise.demo.dto.Discussion.CreateDiscussionDTO;
import arguewise.demo.dto.Discussion.DiscussionResponseDTO;
import arguewise.demo.dto.userDiscussion.JoinDiscussionDTO;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.Space;
import arguewise.demo.model.User;
import arguewise.demo.model.UsersDiscussion;
import arguewise.demo.repository.DiscussionRepository;
import arguewise.demo.repository.UsersDiscussionRepository;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussionTestUtility {

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private UsersDiscussionRepository usersDiscussionRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private Faker faker = new Faker();

    private static final String BASE_URL = "http://localhost:";

    private String getUserDiscussionUrl(int port) {
        return BASE_URL + port + "/api/me/discussions";
    }

    public Discussion createDiscussion(Space space, User user) {
        CreateDiscussionDTO createDiscussionDTO = new CreateDiscussionDTO();
        createDiscussionDTO.setSpaceID(space.getId());
        createDiscussionDTO.setTopic(faker.lorem().sentence(3));
        createDiscussionDTO.setTimeLimit(LocalDateTime.now().plus(1, ChronoUnit.WEEKS));
        Discussion discussion = new Discussion(createDiscussionDTO, user, space);

        discussionRepository.save(discussion);
        return discussion;
    }

    public ResponseEntity<Void> joinDiscussion(String userDiscussionUrl, Long discussionId, UsersDiscussion.Side side, HttpHeaders headers) {
        JoinDiscussionDTO joinDiscussionDTO = new JoinDiscussionDTO();
        joinDiscussionDTO.setSide(side);

        HttpEntity<JoinDiscussionDTO> requestEntity = new HttpEntity<>(joinDiscussionDTO, headers);
        return restTemplate.exchange(userDiscussionUrl + "/" + discussionId + "/join", HttpMethod.POST, requestEntity, Void.class);
    }

    public ResponseEntity<Void> joinDiscussion(Long discussionId, UsersDiscussion.Side side, HttpHeaders headers, int port) {
        JoinDiscussionDTO joinDiscussionDTO = new JoinDiscussionDTO();
        joinDiscussionDTO.setSide(side);

        HttpEntity<JoinDiscussionDTO> requestEntity = new HttpEntity<>(joinDiscussionDTO, headers);
        return restTemplate.exchange(getUserDiscussionUrl(port) + "/" + discussionId + "/join", HttpMethod.POST, requestEntity, Void.class);
    }

    public ResponseEntity<List<DiscussionResponseDTO>> getUserDiscussions(HttpHeaders headers, int port) {
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(getUserDiscussionUrl(port), HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<DiscussionResponseDTO>>() {});
    }

    public ResponseEntity<List<DiscussionResponseDTO>> getUserDiscussions(String userDiscussionUrl, HttpHeaders headers) {
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(userDiscussionUrl, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<DiscussionResponseDTO>>() {});
    }

    public Optional<UsersDiscussion> findUserDiscussionByUserIdAndDiscussionId(int userId, Long id1) {
        return usersDiscussionRepository.findByUserIdAndDiscussionId(userId, id1);
    }
}
