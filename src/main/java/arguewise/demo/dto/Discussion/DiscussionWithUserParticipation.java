package arguewise.demo.dto.Discussion;

import arguewise.demo.model.Discussion;
import arguewise.demo.model.UsersDiscussion;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiscussionWithUserParticipation {
    private Discussion discussion;
    private UsersDiscussion.Side currentUsersSide;
}
