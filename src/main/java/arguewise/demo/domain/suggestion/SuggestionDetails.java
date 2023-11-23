package arguewise.demo.domain.suggestion;

import arguewise.demo.model.Suggestion;
import lombok.Data;

@Data
public class SuggestionDetails {
    private Suggestion suggestion;
    private long numberOfLikes;
    private boolean likedByCurrentUser;

    public SuggestionDetails(Suggestion suggestion, long numberOfLikes, boolean likedByCurrentUser) {
        this.suggestion = suggestion;
        this.numberOfLikes = numberOfLikes;
        this.likedByCurrentUser = likedByCurrentUser;
    }
}
