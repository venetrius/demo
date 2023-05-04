package arguewise.demo.integration;

import arguewise.demo.dto.Discussion.CreateDiscussionDTO;
import arguewise.demo.dto.Discussion.DiscussionResponseDTO;
import arguewise.demo.dto.userDiscussion.JoinDiscussionDTO;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.Space;
import arguewise.demo.model.User;
import arguewise.demo.model.UsersDiscussion;
import com.github.javafaker.Faker;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DiscussionTestUtility {

    public static Discussion createDiscussion(Space space, User user, Faker faker) {
        CreateDiscussionDTO createDiscussionDTO = new CreateDiscussionDTO();
        createDiscussionDTO.setSpaceID(space.getId());
        createDiscussionDTO.setTopic(faker.lorem().sentence(3));
        createDiscussionDTO.setTimeLimit(LocalDateTime.now().plus(1, ChronoUnit.WEEKS));
        Discussion discussion = new Discussion(createDiscussionDTO, user, space);

        return discussion;
    }

    public static ResponseEntity<Void> joinDiscussion(TestRestTemplate restTemplate, String userDiscussionUrl, Long discussionId, UsersDiscussion.Side side, HttpHeaders headers) {
        JoinDiscussionDTO joinDiscussionDTO = new JoinDiscussionDTO();
        joinDiscussionDTO.setSide(side);

        HttpEntity<JoinDiscussionDTO> requestEntity = new HttpEntity<>(joinDiscussionDTO, headers);
        return restTemplate.exchange(userDiscussionUrl + "/" + discussionId + "/join", HttpMethod.POST, requestEntity, Void.class);
    }

    public static ResponseEntity<List<DiscussionResponseDTO>> getUserDiscussions(TestRestTemplate restTemplate, String userDiscussionUrl, HttpHeaders headers) {
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(userDiscussionUrl, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<DiscussionResponseDTO>>() {});
    }

}
