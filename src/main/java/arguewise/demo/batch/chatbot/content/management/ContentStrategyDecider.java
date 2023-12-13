package arguewise.demo.batch.chatbot.content.management;

import arguewise.demo.model.Argument;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.User;
import arguewise.demo.repository.ArgumentRepository;
import arguewise.demo.repository.DiscussionRepository;
import arguewise.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class ContentStrategyDecider {

    private final UserRepository userRepository;

    private final DiscussionRepository discussionRepository;

    private final ArgumentRepository argumentRepository;

    public Actions chooseAction() {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 4);
        return Actions.values()[randomNum];
    }

    public User chooseUser() {
        return null;
//        List<User> users = userRepository.findByIsBot(true);
//        if(users.isEmpty()) {
//            throw new RuntimeException("No bot users found");
//        }
//        return users.get(ThreadLocalRandom.current().nextInt(0, users.size()));
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

    public Discussion chooseUserDiscussion(User user) {
        long count = discussionRepository.countByUserAndStatus(user, Discussion.DiscussionStatus.ACTIVE);
        if (count == 0) {
            throw new RuntimeException("No active discussions found");
        }
        int randomPage = ThreadLocalRandom.current().nextInt(0, (int) count);
        PageRequest singleItem = PageRequest.of(randomPage, 1);

        Page<Discussion> discussionPage = discussionRepository.findByUserAndStatus(user, Discussion.DiscussionStatus.ACTIVE, singleItem);
        if(discussionPage.hasContent()) {
            return discussionPage.getContent().get(0);
        }
        throw new RuntimeException("No active discussion returned");
    }

    public Argument chooseArgument(User user) {
        List<Long> ids = argumentRepository.countByUserAndStatus(user, Discussion.DiscussionStatus.ACTIVE);
        int randomIndex = ThreadLocalRandom.current().nextInt(0, (int) ids.size());
        // list active discussions with argument

        Argument argument = argumentRepository.findById(ids.get(randomIndex)).orElseThrow(() -> new RuntimeException("No active arguments found"));
        return argument;
    }
}
