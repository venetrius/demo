package arguewise.demo.dto.suggestion;

import arguewise.demo.model.Suggestion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateSuggestionDTO {

    private Long argumentId;
    private Suggestion.SuggestionType type;
    private String section;
    private Integer position;
    private String text;
    private String comment;
    private Integer argumentVersion;
}
