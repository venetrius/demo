package arguewise.demo.batch.chatbot.argument;

import arguewise.demo.batch.chatbot.Context;
import arguewise.demo.batch.chatbot.IAiClientWrapper;
import arguewise.demo.dto.argument.CreateArgumentDTO;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.UsersDiscussion;
import arguewise.demo.repository.ArgumentRepository;
import arguewise.demo.service.util.IJsonParser;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArgumentCreator implements IArgumentCreator {
    private final IAiClientWrapper aiServiceProvider;

    private final IJsonParser parser;

    private final ArgumentRepository argumentRepository;

    public ArgumentCreator(IAiClientWrapper aiServiceProvider, IJsonParser parser, ArgumentRepository argumentRepository) {
        this.aiServiceProvider = aiServiceProvider;
        this.parser = parser;
        this.argumentRepository = argumentRepository;
    }
    @Override
    public CreateArgumentDTO createArgument(Discussion discussion, UsersDiscussion.Side side) {
        List<ChatMessage> messages = createMessageList(discussion, side);

        String responseString = aiServiceProvider.getResponse(messages);
        return parseResponse(responseString);
    }

    private List<ChatMessage> createMessageList(Discussion discussion, UsersDiscussion.Side side) {
        ArrayList<ChatMessage> messages = new ArrayList<>();

        String introMessage = Context.neutralParticipant + Context.argueWiseSummary;
        messages.add(new ChatMessage("user",introMessage));

        List<String> argumentTitles = argumentRepository.findTitlesByDiscussionIdAndProSide(discussion.getId(), side);
        messages.add(new ChatMessage(
                "user",
                "existing discussions: \n" + String.join("||, ", argumentTitles)
        ));

        messages.add(new ChatMessage("user", Context.createArgumentPrompt));
        return messages;
    }
    private CreateArgumentDTO parseResponse(String responseMessage) {
        return parser.parseJson(responseMessage, CreateArgumentDTO.class);
    }
}
