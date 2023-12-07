package arguewise.demo.service;

import arguewise.demo.dto.Discussion.CreateDiscussionDTO;
import arguewise.demo.dto.Discussion.DiscussionWithUserParticipation;
import arguewise.demo.dto.Discussion.UpdateDiscussionDTO;
import arguewise.demo.model.Argument;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.User;
import arguewise.demo.model.UsersDiscussion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDiscussionService {
    Page<Discussion> findAll(Pageable pageable);
    DiscussionWithUserParticipation findById(Long id);
    Discussion save(CreateDiscussionDTO discussion);
    Discussion update(Long id, UpdateDiscussionDTO updatedDiscussion);
    boolean deleteById(Long id);

    boolean existsById(Long id);

    void isActiveOrThrow(Discussion discussion);

    public UsersDiscussion.Side getCurrentUsersSide(Discussion discussion);

    UsersDiscussion.Side getSideOfUser(Discussion discussion, User user);
}
