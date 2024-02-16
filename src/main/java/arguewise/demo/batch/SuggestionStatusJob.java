package arguewise.demo.batch;

import arguewise.demo.model.Argument;
import arguewise.demo.model.ArgumentDetail;
import arguewise.demo.model.Suggestion;
import arguewise.demo.repository.ArgumentRepository;
import arguewise.demo.repository.SuggestionRepository;
import arguewise.demo.repository.VoteRepository;
import arguewise.demo.service.VoteService;
import arguewise.demo.types.EntityType;
import arguewise.demo.types.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
@Data
public class SuggestionStatusJob {

    private final SuggestionRepository suggestionRepository;

    private final ArgumentRepository argumentRepository;

    private final VoteRepository voteRepository;

    private final VoteService voteService;

    private final Logger logger = Logger.getLogger(SuggestionStatusJob.class.getName());

    // TODO consider
    @Scheduled(cron = "0 */5 * * * *")
    public void checkForWork() {
        logger.info("Running SuggestionStatusService at: " + LocalDateTime.now());
        Collection<Long> activeSuggestions = suggestionRepository.findIdsByStatus(Suggestion.SuggestionStatus.ACTIVE);

        List<Object[]> votes = voteRepository.findVoteCountsForEntity(activeSuggestions, EntityType.SUGGESTION);

        List<Long> suggestionsToMerge = new ArrayList<>();
        List<Long> suggestionsToReject = new ArrayList<>();

        Map<Long, Long> suggestionToVoteCount = getSugestionIdToCountMap(votes);

        // collect suggestions to merge and reject
        for (Map.Entry<Long, Long> entry : suggestionToVoteCount.entrySet()) {
            Long suggestionId = entry.getKey();
            Long voteCount = entry.getValue();
            if (voteCount < -2) {
                suggestionsToReject.add(suggestionId);
            } else if (voteCount >= 2) {
                suggestionsToMerge.add(suggestionId);
            }
            System.out.println("SuggestionId: " + suggestionId + " voteCount: " + voteCount);
        }


        logger.info("Suggestions to merge: " + suggestionsToMerge.size());
        logger.info("Suggestions to reject: " + suggestionsToReject.size());

        // reject suggestions
        for (Long suggestionId : suggestionsToReject) {
            Suggestion suggestion = suggestionRepository.findById(suggestionId).get();
            suggestion.setStatus(Suggestion.SuggestionStatus.REJECTED);
            suggestionRepository.save(suggestion);
        }

        // merge suggestions
        for (Long suggestionId : suggestionsToMerge) {
            Suggestion suggestion = suggestionRepository.findById(suggestionId).get();
            Argument argument =  argumentRepository.findByIdAndFetchDetailsEagerly(suggestion.getArgument().getId());
            List<ArgumentDetail> details = argument.getArgumentDetails();
            ArgumentDetail detail = details.get(Integer.parseInt(suggestion.getSection()));

            detail.setText(suggestion.getText());
            argumentRepository.save(argument);
            suggestion.setStatus(Suggestion.SuggestionStatus.ACCEPTED);
            suggestionRepository.save(suggestion);
        }

        logger.info("SuggestionStatusService finished at: " + LocalDateTime.now());
    }

    @NotNull
    private static Map<Long, Long> getSugestionIdToCountMap(List<Object[]> votes) {
        Map<Long,Long> suggestionToVoteCount = new HashMap<>();
        // aggregate downvotes and upvotes
        // TODO create a settings class to handle logic related to votes
        for (Object[] vote : votes) {
            Long suggestionId = (Long) vote[0];
            VoteType voteType = (VoteType) vote[1];
            Long count = (Long) vote[2];
            long voteValue = voteType == VoteType.UPVOTE ? count : -count;
            if(!suggestionToVoteCount.containsKey(suggestionId)) {
                suggestionToVoteCount.put(suggestionId, voteValue);
            } else {
                suggestionToVoteCount.put(suggestionId, suggestionToVoteCount.get(suggestionId) + voteValue);
            }
        }
        return suggestionToVoteCount;
    }
}
