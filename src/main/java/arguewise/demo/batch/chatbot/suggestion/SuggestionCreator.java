package arguewise.demo.batch.chatbot.suggestion;

import arguewise.demo.batch.chatbot.Context;
import arguewise.demo.batch.chatbot.IAiClientWrapper;
import arguewise.demo.dto.argument.CreateArgumentDTO;
import arguewise.demo.dto.suggestion.CreateSuggestionDTO;
import arguewise.demo.model.Argument;
import arguewise.demo.model.ArgumentDetail;
import arguewise.demo.model.Suggestion;
import arguewise.demo.repository.ArgumentDetailRepository;
import arguewise.demo.repository.SuggestionRepository;
import arguewise.demo.service.util.IJsonParser;
import com.theokanning.openai.completion.chat.ChatMessage;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class SuggestionCreator implements ISuggestionCreator {

    private final SuggestionRepository suggestionRepository;

    private final ArgumentDetailRepository argumentDetailRepository;

    private final IAiClientWrapper aiServiceProvider;

    private final IJsonParser parser;

    @Override
    public Optional<CreateSuggestionDTO> createSuggestion(Argument argument) {
        try {
            List<ChatMessage> messages = createMessageList(argument);
            String responseString = aiServiceProvider.getResponse(messages);
            CreateSuggestionDTO dto = parseResponse(responseString);
            // make sure argument id is correct
            dto.setArgumentId(argument.getId());
            dto.setType(Suggestion.SuggestionType.REVISION);
            return Optional.of(dto);
        } catch (Error e) {
            return Optional.empty();
        }
    }

    private List<ChatMessage> createMessageList(Argument argument) {
        ArrayList<ChatMessage> messages = new ArrayList<>();

        String introMessage = Context.neutralParticipant + Context.argueWiseSummary;
        messages.add(new ChatMessage("user",introMessage));

        List<ArgumentDetail> argumentDetails = argumentDetailRepository.findAllByArgumentId(argument.getId());
        List<Suggestion> suggestionList = suggestionRepository.findByArgumentId(argument.getId());
        // add argument title
        messages.add(new ChatMessage(
                "user",
                "selected argument: \n" + argument.getTitle() + "\n"
        ));

        // add argument details
        messages.add(new ChatMessage(
                "user",
                "argument details: \n" + String.join("||, ", argumentDetails.stream().map(detail -> {
                    return "section:" + detail.getPosition() + " text: \"" + detail.getText() + "\"\n";
                }).toArray(String[]::new))
        ));
        // add existing suggestions
        messages.add(new ChatMessage(
                "user",
                "existing suggestions for this argument: \n" + String.join("||, ", suggestionList.stream().map(suggestion -> {
                    return "section:" + suggestion.getPosition() + " text: \"" + suggestion.getText() + "\"\n";
                }).toArray(String[]::new))
        ));

        messages.add(new ChatMessage("user", Context.createSuggestionPrompt));
        return messages;
    }

    private CreateSuggestionDTO parseResponse(String responseMessage) {
        return parser.parseJson(responseMessage, CreateSuggestionDTO.class);
    }
}
