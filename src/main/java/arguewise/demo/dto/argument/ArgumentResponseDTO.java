package arguewise.demo.dto.argument;

import arguewise.demo.model.Argument;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArgumentResponseDTO {

    private long id;
    private long discussionID;
    private long authorId;
    private String title;
    private String argumentDetails;
    private String creationTimestamp;

    public ArgumentResponseDTO(Argument argument) {
        this.id = argument.getId();
        this.discussionID = argument.getDiscussion().getId();
        this.authorId = argument.getAuthor().getId();
        this.title = argument.getTitle();
        this.argumentDetails = argument.getArgumentDetails();
        this.creationTimestamp = argument.getCreationTimestamp().toString();
    }
}
