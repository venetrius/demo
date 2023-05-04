package arguewise.demo.dto.userDiscussion;

import arguewise.demo.model.UsersDiscussion;
import lombok.Data;

@Data
public class UserDiscussionDTO {
    private Long id;
    private Integer userId; // TODO change to LONG
    private Long discussionId;
    private String side;

    public UserDiscussionDTO(UsersDiscussion usersDiscussion) {
        this.id = usersDiscussion.getId();
        this.userId = usersDiscussion.getUser().getId();
        this.discussionId = usersDiscussion.getDiscussion().getId();
        this.side = usersDiscussion.getSide().toString();
    }
}
