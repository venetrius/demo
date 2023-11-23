package arguewise.demo.service;

import arguewise.demo.dto.suggestion.CreateSuggestionDTO;
import arguewise.demo.exception.NotFoundException;
import arguewise.demo.model.Argument;
import arguewise.demo.model.Suggestion;
import arguewise.demo.model.User;
import arguewise.demo.repository.ArgumentRepository;
import arguewise.demo.repository.SuggestionRepository;
import arguewise.demo.security.utils.SecurityUtils;
import arguewise.demo.types.EntityType;
import arguewise.demo.types.VoteType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SuggestionService implements ISuggestionService {

    private final SuggestionRepository suggestionRepository;
    private final ArgumentRepository argumentRepository;
    private final VoteService voteService;

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

    public Suggestion getSuggestion(Long id) {
        return suggestionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Suggestion not found"));
    }

    public List<Suggestion> getSuggestionBy(Long argumentId) {
        return suggestionRepository.findByArgumentId(argumentId);
    }

    @Override
    public boolean canVote(Long argumentId, String email) {
        Argument argument = argumentRepository.findById(argumentId)
                .orElseThrow(() -> new NotFoundException("Argument not found"));
        User user = SecurityUtils.getCurrentUser();
        return false;
    }

    @Override
    public void vote(Long suggestionId, VoteType vote) {
        voteService.castVote(suggestionId, EntityType.SUGGESTION, vote);
    }

    @Override
    public Optional<Suggestion> findById(Long id) {
        return suggestionRepository.findById(id);
    }
}
