package arguewise.demo.batch.chatbot.suggestion;

import arguewise.demo.dto.suggestion.CreateSuggestionDTO;
import arguewise.demo.model.Argument;

import java.util.Optional;

public interface ISuggestionCreator {
    Optional<CreateSuggestionDTO> createSuggestion(Argument argument);
}
