package arguewise.demo.domain;

import arguewise.demo.model.Argument;
import lombok.Data;

@Data
public class ArgumentDetails {
    private Argument argument;
    private long numberOfLikes;
    private boolean likedByCurrentUser;

    public ArgumentDetails(Argument argument, long numberOfLikes, boolean likedByCurrentUser) {
        this.argument = argument;
        this.numberOfLikes = numberOfLikes;
        this.likedByCurrentUser = likedByCurrentUser;
    }
}
