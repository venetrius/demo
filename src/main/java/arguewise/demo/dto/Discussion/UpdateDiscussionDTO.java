package arguewise.demo.dto.Discussion;
import arguewise.demo.model.Discussion;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDiscussionDTO {
    @Size(max = 100, message = "Topic should be no longer than 100 characters")
    private String topic;
    private LocalDateTime timeLimit;

    public boolean isUpToDate(Discussion discussion) {
        boolean isTopicUpToDate = (this.topic == null) || this.topic.equals(discussion.getTopic());
        boolean isTimeLimitUpToDate = (this.timeLimit == null) || this.timeLimit.equals(discussion.getTimeLimit());
        return isTopicUpToDate && isTimeLimitUpToDate;
    }

}
