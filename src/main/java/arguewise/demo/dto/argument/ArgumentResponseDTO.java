package arguewise.demo.dto.argument;

import arguewise.demo.domain.ArgumentDetails;
import arguewise.demo.model.Argument;
import arguewise.demo.model.ArgumentDetail;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
public class ArgumentResponseDTO {

    private long id;
    private long discussionID;
    private long authorId;
    private String title;
    private List<String> argumentDetails;
    private String creationTimestamp;
    private long numberOfLikes;
    private boolean isLikedByCurrentUser;

    public ArgumentResponseDTO(Argument argument) {
        this.id = argument.getId();
        this.discussionID = argument.getDiscussion().getId();
        this.authorId = argument.getAuthor().getId();
        this.title = argument.getTitle();
        this.argumentDetails = formatArgumentDetails(argument.getArgumentDetails());
        this.creationTimestamp = argument.getCreationTimestamp().toString();
        this.numberOfLikes = 0l;
        this.isLikedByCurrentUser = false;
    }

    public ArgumentResponseDTO(ArgumentDetails argumentDetails) {
        Argument argument = argumentDetails.getArgument();
        this.id = argument.getId();
        this.discussionID = argument.getDiscussion().getId();
        this.authorId = argument.getAuthor().getId();
        this.title = argument.getTitle();
        this.argumentDetails = formatArgumentDetails(argument.getArgumentDetails());
        this.creationTimestamp = argument.getCreationTimestamp().toString();
        this.numberOfLikes = argumentDetails.getNumberOfLikes();
        this.isLikedByCurrentUser = argumentDetails.isLikedByCurrentUser();
    }

    // TODO only works if position follows an arithmetic progression with a starting value of 1
    private List<String> formatArgumentDetails(List<ArgumentDetail> details) {
        String[] res = new String[details.size()];
        details.forEach(
                a -> res[a.getPosition() - 1] = a.getText()
        );
        return  Arrays.stream(res).toList();
    }
}
