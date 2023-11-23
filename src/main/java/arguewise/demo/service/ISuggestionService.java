package arguewise.demo.service;

import arguewise.demo.domain.suggestion.SuggestionDetails;
import arguewise.demo.dto.suggestion.CreateSuggestionDTO;
import arguewise.demo.model.Suggestion;
import arguewise.demo.types.VoteType;

import java.util.List;
import java.util.Optional;

public interface ISuggestionService {
    public Suggestion createSuggestion(CreateSuggestionDTO dto);

    Suggestion getSuggestion(Long id);

    List<SuggestionDetails> getSuggestionBy(Long argumentId);

    boolean canVote(Long id, String email);

    void vote(Long suggestionId, VoteType vote);

    Optional<Suggestion> findById(Long id);
}
