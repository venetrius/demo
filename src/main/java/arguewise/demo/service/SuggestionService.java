package arguewise.demo.service;

import arguewise.demo.domain.suggestion.SuggestionDetails;
import arguewise.demo.dto.suggestion.CreateSuggestionDTO;
import arguewise.demo.exception.NotFoundException;
import arguewise.demo.model.Argument;
import arguewise.demo.model.ArgumentDetail;
import arguewise.demo.model.Suggestion;
import arguewise.demo.model.User;
import arguewise.demo.repository.ArgumentRepository;
import arguewise.demo.repository.SuggestionRepository;
import arguewise.demo.security.utils.SecurityUtils;
import arguewise.demo.types.EntityType;
import arguewise.demo.types.VoteType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        suggestion.setSection(getSection(dto, argument));
        suggestion.setPosition(dto.getPosition());
        suggestion.setText(dto.getText());
        suggestion.setComment(dto.getComment());
        suggestion.setArgumentVersion(dto.getArgumentVersion());
        suggestion.setStatus(Suggestion.SuggestionStatus.ACTIVE);

        return suggestionRepository.save(suggestion);
    }

// TODO also update suggestionStatus service !
    private String getSection(CreateSuggestionDTO dto, Argument argument) {
        if(dto.getType() == Suggestion.SuggestionType.REVISION) {
            return dto.getSection();
        }
        return String.valueOf(argument.getArgumentDetails().size() + 1);
    }

    public Suggestion getSuggestion(Long id) {
        return suggestionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Suggestion not found"));
    }

    public List<SuggestionDetails> getSuggestionBy(Long argumentId) {
        return  decorateSuggestions(suggestionRepository.findByArgumentId(argumentId), SecurityUtils.getCurrentUser().getId());
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

    @Override
    public void deleteVote(Long suggestionId) {
        voteService.deleteVote(suggestionId, EntityType.SUGGESTION);
    }

    private List<SuggestionDetails> decorateSuggestions(List<Suggestion> suggestions, long userId) {
        if(suggestions.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> suggestionIds = suggestions.stream().map(Suggestion::getId).toList();

        List<Object[]> upVotes = voteService.getNumberOfVotesForEntities(EntityType.SUGGESTION, suggestionIds, VoteType.UPVOTE);
        Map<Long, Long> likeCountsMap = upVotes.stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],  // entityId
                        row -> (Long) row[1])  // count
                );

        Set<Long> likedArgumentIdsSet = new HashSet<>(voteService.findLikedByUserForEntities(userId, suggestionIds, EntityType.SUGGESTION, VoteType.UPVOTE));

        List<Object[]> downVotes = voteService.getNumberOfVotesForEntities(EntityType.SUGGESTION, suggestionIds, VoteType.DOWNVOTE);
        Map<Long, Long> dislikeCountMap = downVotes.stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],  // entityId
                        row -> (Long) row[1])  // count
                );

        return suggestions.stream()
                .map(suggestion -> {
                    long likesCount = likeCountsMap.getOrDefault(suggestion.getId(), 0L);
                    long dislikesCount = dislikeCountMap.getOrDefault(suggestion.getId(), 0L);
                    boolean likedByUser = likedArgumentIdsSet.contains(suggestion.getId());
                    return new SuggestionDetails(suggestion, likesCount, dislikesCount ,likedByUser);
                })
                .toList();
    }
}
