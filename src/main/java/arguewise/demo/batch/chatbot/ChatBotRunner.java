package arguewise.demo.batch.chatbot;

import arguewise.demo.batch.chatbot.argument.ArgumentCreator;
import arguewise.demo.batch.chatbot.content.management.Actions;
import arguewise.demo.batch.chatbot.content.management.ContentStrategyDecider;
import arguewise.demo.batch.chatbot.space.SpaceVoter;
import arguewise.demo.batch.chatbot.suggestion.ISuggestionCreator;
import arguewise.demo.dto.Discussion.CreateDiscussionDTO;
import arguewise.demo.dto.Discussion.DiscussionResponseDTO;
import arguewise.demo.dto.argument.ArgumentResponseDTO;
import arguewise.demo.dto.argument.CreateArgumentDTO;
import arguewise.demo.dto.suggestion.CreateSuggestionDTO;
import arguewise.demo.dto.suggestion.SuggestionResponseDTO;
import arguewise.demo.exception.NotFoundException;
import arguewise.demo.model.*;

import arguewise.demo.repository.*;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    private ISuggestionCreator suggestionCreator;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsersDiscussionRepository usersDiscussionRepository;

    @Autowired
    private SuggestionRepository suggestionRepository;

    @Autowired
    private ArgumentCreator argumentCreator;

    @Autowired
    private SpaceVoter spaceVoter;

    @Autowired
    private Gson gson;

    @Autowired
    private ContentStrategyDecider contentStrategyDecider;

// @Scheduled(cron = "0 */30 * * * *")
    public String run() {
        logger.info("Running ChatBotRunner at: " + java.time.LocalDateTime.now());
        User user = contentStrategyDecider.chooseUser();
        Actions action = contentStrategyDecider.chooseAction();

        logger.info("Selected action:" + action);

        try {
            String result = switch (action) {
                case CREATE_NEW_ARGUMENT -> createArgument(user);
                case CREATE_NEW_DISCUSSION -> createDiscussion(user);
                case CREATE_NEW_SUGGESTION -> createSuggestion(user);
                case VOTE_SPACE -> spaceVoter.voteSpace(user);
                default -> throw new RuntimeException("Not implemented yet");
            };
            logger.info("ChatBotRunner finished at: " + java.time.LocalDateTime.now());
            return result;
        } catch (Exception e) {
            logger.error("Error while performing action: " + action + " for user: " + user.getEmail());
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
        // TODO should set side based on response from the AI in argumentCreator
        UsersDiscussion.Side side = UsersDiscussion.Side.PRO;
        createUserDiscussion(user, discussion, side);
        CreateArgumentDTO createArgumentDTO = argumentCreator.createArgument(discussion, side);
        Argument argument = argumentRepository.save(new Argument(createArgumentDTO, user, side, discussion));
        System.out.println(gson.toJson(new ArgumentResponseDTO(argument)));
        return gson.toJson(new ArgumentResponseDTO(argument));
    }

    private void createUserDiscussion(User user, Discussion discussion, UsersDiscussion.Side side) {
        Optional<UsersDiscussion> usersDiscussionOptional = usersDiscussionRepository.findByUserAndDiscussion(user, discussion);
        if(usersDiscussionOptional.isEmpty()) {
            usersDiscussionRepository.save(new UsersDiscussion(user, discussion, side));
        } else {
            UsersDiscussion usersDiscussion = usersDiscussionOptional.get();
            if(usersDiscussion.getSide() != side) {
                throw new RuntimeException("Side does not match when creating argument: " + usersDiscussion.getSide() + " vs " + side);
            }
        }
    }

    private String createSuggestion(User user) {
        Optional<Argument> argument = contentStrategyDecider.chooseArgument(user);
        if(argument.isEmpty()) {
            return null;
        }

        Optional<CreateSuggestionDTO> createSuggestionDTO = suggestionCreator.createSuggestion(argument.get());
        if(createSuggestionDTO.isEmpty()) {
            return "Failed to create suggestion";
        }
        
        Suggestion suggestion = createSuggestion(createSuggestionDTO.get(), user);
        return gson.toJson(new SuggestionResponseDTO(suggestion));
    }

    public Suggestion createSuggestion(CreateSuggestionDTO dto, User user) {
        Argument argument = argumentRepository.findById(dto.getArgumentId())
                .orElseThrow(() -> new NotFoundException("Argument not found"));

        Suggestion suggestion = new Suggestion();
        suggestion.setArgument(argument);
        suggestion.setUser(user);
        suggestion.setType(dto.getType());
        suggestion.setSection(dto.getSection());
        suggestion.setPosition(dto.getPosition());
        suggestion.setText(dto.getText());
        suggestion.setComment(dto.getComment());
        suggestion.setArgumentVersion(dto.getArgumentVersion());
        suggestion.setStatus(Suggestion.SuggestionStatus.ACTIVE);

        return suggestionRepository.save(suggestion);
    }


}
