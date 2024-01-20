package arguewise.demo.batch.chatbot.content.management;

import arguewise.demo.model.Argument;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.User;
import arguewise.demo.model.UsersDiscussion;
import arguewise.demo.repository.ArgumentRepository;
import arguewise.demo.repository.DiscussionRepository;
import arguewise.demo.repository.UserRepository;
import arguewise.demo.repository.UsersDiscussionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
// TODO TODO remove transactional after addressing: should use UsersDiscussion.getDiscussionId instead of UserDiscussion.getDiscussion().getId()
@Transactional
@AllArgsConstructor
public class ContentStrategyDecider {

    private final UserRepository userRepository;

    private final DiscussionRepository discussionRepository;

    private final ArgumentRepository argumentRepository;

    private final UsersDiscussionRepository usersDiscussionRepository;

    public Actions chooseAction() {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 4);
        return Actions.values()[randomNum];
    }

    public User chooseUser() {
//        return null;
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

    public Optional<Argument> chooseArgument(User user) {
        // get arguments
        // -- active
        // -- in users discussion
        // -- discussion is active
        // -- users side

        // get usersDiscussions
        List<UsersDiscussion> usersDiscussions = usersDiscussionRepository.findActiveByUserId(user.getId());
        System.out.println("Number of usersDiscussions: " + usersDiscussions.size() + " for user with id: " + user.getId());

        if(usersDiscussions.isEmpty()) {
            return Optional.empty();
        }

        // TODO TODO TODO should use UsersDiscussion.getDiscussionId instead of UserDiscussion.getDiscussion().getId()
        List<Long> proDiscussionIds = usersDiscussions.stream().filter(ud -> ud.getSide() == UsersDiscussion.Side.PRO).map(UsersDiscussion::getDiscussion).map(Discussion::getId).toList();
        List<Long> constDiscussionIds = usersDiscussions.stream().filter(ud -> ud.getSide() == UsersDiscussion.Side.CONTRA).map(UsersDiscussion::getDiscussion).map(Discussion::getId).toList();
        List<Argument> proArguments = argumentRepository.findAllByDiscussionIdInAndSide(proDiscussionIds, UsersDiscussion.Side.PRO);
        List<Argument> conArguments = argumentRepository.findAllByDiscussionIdInAndSide(constDiscussionIds, UsersDiscussion.Side.CONTRA);

        int totalSize = proArguments.size() + conArguments.size();
        Argument selectedArgument = null;

        if (totalSize > 0) {
            int randomIndex = ThreadLocalRandom.current().nextInt(0, totalSize);

            if (randomIndex < proArguments.size()) {
                selectedArgument = proArguments.get(randomIndex);
            } else {
                selectedArgument = conArguments.get(randomIndex - proArguments.size());
            }
        }
        return Optional.ofNullable(selectedArgument);
    }
}
