package arguewise.demo.batch;

import arguewise.demo.model.Discussion;
import arguewise.demo.repository.DiscussionRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Data
@NoArgsConstructor
public class DiscussionStateService {
    @Autowired
    private DiscussionRepository discussionRepository;

    @Scheduled(cron = "0 * * * * *")
    public void checkForWork() {
        System.out.println("Running DiscussionStateService at: " + LocalDateTime.now());
        List<Discussion> list = discussionRepository.findByStatusAndTimeLimitBefore(Discussion.DiscussionStatus.ACTIVE, LocalDateTime.now());
    }
}
