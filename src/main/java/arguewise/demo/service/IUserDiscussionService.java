package arguewise.demo.service;

import arguewise.demo.dto.userDiscussion.JoinDiscussionDTO;
import arguewise.demo.model.Discussion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserDiscussionService {
    void joinDiscussion(Long discussionId, JoinDiscussionDTO joinDiscussionDTO);

    Page<Discussion> findDiscussionsByUserId(Long discussionId, Pageable pageable);

    Page<Discussion> getRecommendedUserDiscussions(Pageable pageable);
}
