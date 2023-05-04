package arguewise.demo.service;

import arguewise.demo.dto.userDiscussion.JoinDiscussionDTO;
import arguewise.demo.model.UsersDiscussion;

import java.util.List;

public interface IUserDiscussionService {
    void joinDiscussion(Long discussionId, JoinDiscussionDTO joinDiscussionDTO);
    List<UsersDiscussion> findByUserId(Long userId);
    List<UsersDiscussion> findByDiscussionId(Long discussionId);
}
