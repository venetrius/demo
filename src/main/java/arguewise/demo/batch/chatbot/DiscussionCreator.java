package arguewise.demo.batch.chatbot;

import arguewise.demo.dto.Discussion.CreateDiscussionDTO;
import arguewise.demo.model.Space;
import arguewise.demo.service.discussion.ISpaceDiscussionDataAggregator;
import arguewise.demo.service.util.IJsonParser;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// TODO error handling
// TODO parsing should be done in the service layer (also move inner class)
@Service
public class DiscussionCreator {

    private final IJsonParser parser;

    private final IAiClientWrapper aiServiceProvider;

    private final ISpaceDiscussionDataAggregator spaceDiscussionDataAggregator;

    @Autowired
    public DiscussionCreator(IJsonParser parser, IAiClientWrapper aiServiceProvider,
                             ISpaceDiscussionDataAggregator spaceDiscussionDataAggregator) {
        this.parser = parser;
        this.aiServiceProvider = aiServiceProvider;
        this.spaceDiscussionDataAggregator = spaceDiscussionDataAggregator;
    }

    public CreateDiscussionDTO createDiscussion(Space space) {
        List<ChatMessage> messages = createMessageList(space);

        String responseMessage = aiServiceProvider.getResponse(messages);

        return parseResponse(responseMessage);
    }

    private List<ChatMessage> createMessageList(Space space) {
        ArrayList<ChatMessage> messages = new ArrayList<>();
        String introMessage = Context.neutralParticipant + Context.argueWiseSummary;
        messages.add(new ChatMessage("user",introMessage));
        Collection<String> existingDiscussions = spaceDiscussionDataAggregator.discussionTitles(space);
        messages.add(new ChatMessage(
                "user",
                "existing discussions: \n" + String.join("||, ", existingDiscussions)
        ));
        messages.add(new ChatMessage("user", Context.createTopicPrompt));
        return messages;
    }

    private CreateDiscussionDTO parseResponse(String responseMessage) {
        InputData data = parser.parseJson(responseMessage, InputData.class);
        DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(data.timeLimit, inputFormatter);
        return new CreateDiscussionDTO(data.spaceID, data.topic, data.description, dateTime);
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public class InputData {
        public long spaceID;
        public String topic;
        public String description;
        public String timeLimit;

    }
}
