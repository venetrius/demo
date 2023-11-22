package arguewise.demo.service;

import arguewise.demo.dto.suggestion.CreateSuggestionDTO;
import arguewise.demo.model.Suggestion;

import java.util.List;

public interface ISuggestionService {
    public Suggestion createSuggestion(CreateSuggestionDTO dto);

    Suggestion getSuggestion(Long id);

    List<Suggestion> getSuggestionBy(Long argumentId);
}
