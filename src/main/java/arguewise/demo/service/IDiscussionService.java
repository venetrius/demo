package arguewise.demo.service;

import arguewise.demo.dto.Discussion.CreateDiscussionDTO;
import arguewise.demo.dto.Discussion.DiscussionWithUserParticipation;
import arguewise.demo.dto.Discussion.UpdateDiscussionDTO;
import arguewise.demo.model.Discussion;

import java.util.List;

public interface IDiscussionService {
    List<Discussion> findAll();
    DiscussionWithUserParticipation findById(Long id);
    Discussion save(CreateDiscussionDTO discussion);
    Discussion update(Long id, UpdateDiscussionDTO updatedDiscussion);
    boolean deleteById(Long id);

    boolean existsById(Long id);

    void isActiveOrThrow(Discussion discussion);
}
