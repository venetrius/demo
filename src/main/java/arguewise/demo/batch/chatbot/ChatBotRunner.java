package arguewise.demo.batch.chatbot;

import arguewise.demo.batch.chatbot.argument.ArgumentCreator;
import arguewise.demo.dto.Discussion.CreateDiscussionDTO;
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

    // TODO enable this after testing
//    @Scheduled(cron = "0 */5 * * * *")
    public String run() {
        return createArgument();
//        return createDiscussion();
    }

    private String getArguments() {

        return "";
    }

    private String createDiscussion() {
        Space space = spaceRepository.findAll().get(0);
        User user = userRepository.findAll().get(0);
        CreateDiscussionDTO createDiscussionDTO = discussionCreator.createDiscussion(space);
        System.out.println(gson.toJson(createDiscussionDTO));
        Discussion discussion = discussionRepository.save(new Discussion(createDiscussionDTO, user, space));
        return gson.toJson(discussion);
    }

    private String createArgument() {
        Discussion discussion = discussionRepository.findByStatus(Discussion.DiscussionStatus.ACTIVE).get(0);
        UsersDiscussion.Side side = UsersDiscussion.Side.PRO;
        User user = userRepository.findAll().get(0);

        CreateArgumentDTO createArgumentDTO = argumentCreator.createArgument(discussion, side);
        Argument argument = argumentRepository.save(new Argument(createArgumentDTO, user, discussion));
        System.out.println(gson.toJson(new ArgumentResponseDTO(argument)));
        return gson.toJson(new ArgumentResponseDTO(argument));
    }

}
