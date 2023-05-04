package arguewise.demo.service;

import arguewise.demo.dto.userDiscussion.JoinDiscussionDTO;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.UsersDiscussion;

import java.util.List;

public interface IUserDiscussionService {
    void joinDiscussion(Long discussionId, JoinDiscussionDTO joinDiscussionDTO);
    List<Discussion> findDiscussionsByUserId(Long discussionId);
}
