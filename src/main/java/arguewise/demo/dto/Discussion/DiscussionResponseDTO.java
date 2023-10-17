package arguewise.demo.dto.Discussion;

import arguewise.demo.model.Discussion;
import arguewise.demo.model.UsersDiscussion;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiscussionResponseDTO {

    private long id;
    private long spaceID;

    private long creatorId;

    private String creatorName;
    
    private String topic;

    private String description;

    private String creationTimestamp;

    private String timeLimit;

    private String status;

    private UsersDiscussion.Side currentUsersSide;

    public DiscussionResponseDTO(Discussion discussion) {
        this.id = discussion.getId();
        this.spaceID = discussion.getSpace().getId();
        this.creatorId = discussion.getCreator().getId();
        this.topic = discussion.getTopic();
        this.description = discussion.getDescription();
        this.creationTimestamp = discussion.getCreationTimestamp().toString();
        this.timeLimit = discussion.getTimeLimit().toString();
        this.status = discussion.getStatus().toString();
    }

    public DiscussionResponseDTO(DiscussionWithUserParticipation discussionWithUserParticipation) {
        Discussion discussion = discussionWithUserParticipation.getDiscussion();
        this.id = discussion.getId();
        this.spaceID = discussion.getSpace().getId();
        this.creatorId = discussion.getCreator().getId();
        this.creatorName = discussionWithUserParticipation.getCreatorName();
        this.topic = discussion.getTopic();
        this.description = discussion.getDescription();
        this.creationTimestamp = discussion.getCreationTimestamp().toString();
        this.timeLimit = discussion.getTimeLimit().toString();
        this.status = discussion.getStatus().toString();
        this.currentUsersSide = discussionWithUserParticipation.getCurrentUsersSide();
    }
}