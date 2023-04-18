package arguewise.demo.dto.Discussion;

import arguewise.demo.model.Discussion;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiscussionResponseDTO {

    private long id;
    private long spaceID;

    private long creatorId;

    private String topic;

    private String creationTimestamp;

    private String timeLimit;

    private String status;

    public DiscussionResponseDTO(Discussion discussion) {
        this.id = discussion.getId();
        this.spaceID = discussion.getSpace().getId();
        this.creatorId = discussion.getCreator().getId();
        this.topic = discussion.getTopic();
        this.creationTimestamp = discussion.getCreationTimestamp().toString();
        this.timeLimit = discussion.getTimeLimit().toString();
        this.status = discussion.getStatus().toString();
    }

}