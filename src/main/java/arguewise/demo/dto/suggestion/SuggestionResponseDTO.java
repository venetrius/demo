package arguewise.demo.dto.suggestion;

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
    }
}
