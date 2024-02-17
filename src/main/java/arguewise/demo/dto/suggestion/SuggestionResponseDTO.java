package arguewise.demo.dto.suggestion;

import arguewise.demo.domain.suggestion.SuggestionDetails;
import arguewise.demo.model.Suggestion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SuggestionResponseDTO {

    private Long id;
    private Long argumentId;
    private Integer userId;
    private Suggestion.SuggestionType type;
    private String section;
    private Integer position;
    private String text;
    private String comment;
    private Integer argumentVersion;
    private LocalDateTime createdTimestamp;
    private Long numberOfLikes;
    private Long numberOfDislikes;
    private boolean isLikedByCurrentUser;
    private Suggestion.SuggestionStatus status;

    public SuggestionResponseDTO(Suggestion suggestion) {
        this.id = suggestion.getId();
        this.argumentId = suggestion.getArgument().getId();
        this.userId = suggestion.getUser().getId();
        this.type = suggestion.getType();
        this.section = suggestion.getSection();
        this.position = suggestion.getPosition();
        this.text = suggestion.getText();
        this.comment = suggestion.getComment();
        this.argumentVersion = suggestion.getArgumentVersion();
        this.createdTimestamp = suggestion.getCreatedTimestamp();
        this.status = suggestion.getStatus();
    }

    public SuggestionResponseDTO(SuggestionDetails suggestionDetails) {
        Suggestion suggestion = suggestionDetails.getSuggestion();
        this.id = suggestion.getId();
        this.argumentId = suggestion.getArgument().getId();
        this.userId = suggestion.getUser().getId();
        this.type = suggestion.getType();
        this.section = suggestion.getSection();
        this.position = suggestion.getPosition();
        this.text = suggestion.getText();
        this.comment = suggestion.getComment();
        this.argumentVersion = suggestion.getArgumentVersion();
        this.createdTimestamp = suggestion.getCreatedTimestamp();
        this.status = suggestionDetails.getSuggestion().getStatus();

        this.numberOfLikes = suggestionDetails.getNumberOfLikes();
        this.numberOfDislikes = suggestionDetails.getNumberOfDislikes();
        this.isLikedByCurrentUser = suggestionDetails.isLikedByCurrentUser();
    }
}
