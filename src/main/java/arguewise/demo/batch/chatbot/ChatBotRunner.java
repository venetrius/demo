package arguewise.demo.batch.chatbot;

import arguewise.demo.batch.chatbot.argument.ArgumentCreator;
import arguewise.demo.batch.chatbot.content.management.Actions;
import arguewise.demo.batch.chatbot.content.management.ContentStrategyDecider;
import arguewise.demo.dto.Discussion.CreateDiscussionDTO;
import arguewise.demo.dto.Discussion.DiscussionResponseDTO;
import arguewise.demo.dto.argument.ArgumentResponseDTO;
import arguewise.demo.dto.argument.CreateArgumentDTO;
import arguewise.demo.model.Argument;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.Space;
import arguewise.demo.model.User;
import arguewise.demo.model.UsersDiscussion;
import arguewise.demo.repository.ArgumentRepository;
import arguewise.demo.repository.DiscussionRepository;
import arguewise.demo.repository.SpaceRepository;
import arguewise.demo.repository.UserRepository;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ChatBotRunner {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(ChatBotRunner.class);

    @Autowired
    private DiscussionCreator discussionCreator;

    @Autowired
    private ArgumentRepository argumentRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArgumentCreator argumentCreator;

    @Autowired
    private Gson gson;

    @Autowired
    private ContentStrategyDecider contentStrategyDecider;

    @Scheduled(cron = "0 */30 * * * *")
    public String run() {
        logger.info("Running ChatBotRunner at: " + java.time.LocalDateTime.now());
        User user = contentStrategyDecider.chooseUser();
        Actions action = contentStrategyDecider.chooseAction();
        try {
            String result;
            switch (action) {
                case CREATE_NEW_ARGUMENT:
                    result = createArgument(user);
                    break;
                case CREATE_NEW_DISCUSSION:
                    result = createDiscussion(user);
                    break;
                case CREATE_NEW_SUGGESTION:
                    result = createSuggestion(user);
                    break;
                default:
                    throw new RuntimeException("Not implemented yet");
            }
            logger.info("ChatBotRunner finished at: " + java.time.LocalDateTime.now());
            return result;
        } catch (Exception e) {
            logger.error("Error while performing action: " + action + " for user: " + user.getEmail());
            e.printStackTrace();
            throw e;
        }

    }

    private String createDiscussion(User user) {
        Space space = spaceRepository.findAll().get(0);
        CreateDiscussionDTO createDiscussionDTO = discussionCreator.createDiscussion(space);
        System.out.println(gson.toJson(createDiscussionDTO));
        Discussion discussion = discussionRepository.save(new Discussion(createDiscussionDTO, user, space));
        return gson.toJson(new DiscussionResponseDTO(discussion));
    }

    private String createArgument(User user) {

        Discussion discussion = contentStrategyDecider.chooseDiscussion();
        UsersDiscussion.Side side = UsersDiscussion.Side.PRO;

        CreateArgumentDTO createArgumentDTO = argumentCreator.createArgument(discussion, side);
        Argument argument = argumentRepository.save(new Argument(createArgumentDTO, user, discussion));
        System.out.println(gson.toJson(new ArgumentResponseDTO(argument)));
        return gson.toJson(new ArgumentResponseDTO(argument));
    }

    private String createSuggestion(User user) {
        return null;
    }
}
