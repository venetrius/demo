package arguewise.demo.domain.suggestion;

import arguewise.demo.model.Suggestion;
import lombok.Data;

@Data
public class SuggestionDetails {
    private Suggestion suggestion;
    private long numberOfLikes;
    private long numberOfDislikes;
    private boolean likedByCurrentUser;

    public SuggestionDetails(Suggestion suggestion, long numberOfLikes, long numberOfDislikes, boolean likedByCurrentUser) {
        this.suggestion = suggestion;
        this.numberOfLikes = numberOfLikes;
        this.numberOfDislikes = numberOfDislikes;
        this.likedByCurrentUser = likedByCurrentUser;
    }
}
