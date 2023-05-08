package arguewise.demo.service;

import arguewise.demo.dto.suggestion.CreateSuggestionDTO;
import arguewise.demo.model.Suggestion;

public interface ISuggestionService {
    public Suggestion createSuggestion(CreateSuggestionDTO dto);

    Suggestion getSuggestion(Long id);
}
