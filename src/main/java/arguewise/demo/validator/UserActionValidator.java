package arguewise.demo.validator;

import arguewise.demo.dto.Discussion.DiscussionWithUserParticipation;
import arguewise.demo.model.Discussion;
import arguewise.demo.service.IDiscussionService;
import org.springframework.stereotype.Service;

@Service
public class UserActionValidator {

    private final IDiscussionService discussionService;

    public UserActionValidator(IDiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    /**
     * Validate if the user can create an argument for a given discussion.
     *
     * @param userId        The ID of the user attempting the action.
     * @param discussionId  The ID of the target discussion.
     * @return true if the user can create an argument, false otherwise.
     */
    public boolean canCreateArgument(int userId, Long discussionId) {
        DiscussionWithUserParticipation discussion = discussionService.findById(discussionId);
        if (discussion == null) {
            return false;
        }
        if (discussion.getDiscussion().getStatus() != Discussion.DiscussionStatus.ACTIVE) {
            return false;
        }
        if(discussion.getCurrentUsersSide() == null) {
            return false;
        }
        return true;
    }

}
