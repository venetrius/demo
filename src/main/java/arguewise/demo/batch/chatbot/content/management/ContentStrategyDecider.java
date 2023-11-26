package arguewise.demo.batch.chatbot.content.management;

import arguewise.demo.model.Discussion;
import arguewise.demo.model.User;
import arguewise.demo.repository.DiscussionRepository;
import arguewise.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class ContentStrategyDecider {

    private final UserRepository userRepository;

    private final DiscussionRepository discussionRepository;

    public Actions chooseAction() {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 3);
        return Actions.values()[randomNum];
    }

    public User chooseUser() {
        List<User> users = userRepository.findByIsBot(true);
        if(users.isEmpty()) {
            throw new RuntimeException("No bot users found");
        }
        return users.get(ThreadLocalRandom.current().nextInt(0, users.size()));
    }

    public Discussion chooseDiscussion() {
        long count = discussionRepository.countByStatus(Discussion.DiscussionStatus.ACTIVE);
        if (count == 0) {
            throw new RuntimeException("No active discussions found");
        }

        int randomPage = ThreadLocalRandom.current().nextInt(0, (int) count);
        PageRequest singleItem = PageRequest.of(randomPage, 1);
        // TODO - potential bug - number of active discussions can change between count and findByStatus
        Page<Discussion> discussionPage = discussionRepository.findByStatus(Discussion.DiscussionStatus.ACTIVE, singleItem);

        if (discussionPage.hasContent()) {
            return discussionPage.getContent().get(0);
        } else {
            throw new RuntimeException("No active discussions found");
        }
    }

}
