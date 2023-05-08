package arguewise.demo.service;

import arguewise.demo.dto.suggestion.CreateSuggestionDTO;
import arguewise.demo.exception.NotFoundException;
import arguewise.demo.model.Argument;
import arguewise.demo.model.Suggestion;
import arguewise.demo.model.User;
import arguewise.demo.repository.ArgumentRepository;
import arguewise.demo.repository.SuggestionRepository;
import arguewise.demo.security.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SuggestionService {

    private final SuggestionRepository suggestionRepository;
    private final ArgumentRepository argumentRepository;

    public Suggestion createSuggestion(CreateSuggestionDTO dto) {
        User user = SecurityUtils.getCurrentUser();
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

        return suggestionRepository.save(suggestion);
    }
}
