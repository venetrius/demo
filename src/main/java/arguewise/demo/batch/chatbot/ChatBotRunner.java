package arguewise.demo.batch.chatbot;

import arguewise.demo.dto.Discussion.CreateDiscussionDTO;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.Space;
import arguewise.demo.model.User;
import arguewise.demo.repository.SpaceRepository;
import arguewise.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ChatBotRunner {

    @Autowired
    private DiscussionCreator discussionCreator;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private UserRepository userRepository;

    // TODO enable this after testing
//    @Scheduled(cron = "0 */5 * * * *")
    public Discussion run() {
        Space space = spaceRepository.findAll().get(0);
        User user = userRepository.findAll().get(0);
        CreateDiscussionDTO createDiscussionDTO = discussionCreator.createDiscussion(space);
        Discussion discussion = new Discussion(createDiscussionDTO, user, space);
        return discussion;
    }
}
