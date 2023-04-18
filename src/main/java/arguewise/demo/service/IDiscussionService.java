package arguewise.demo.service;

import arguewise.demo.dto.Discussion.CreateDiscussionDTO;
import arguewise.demo.dto.Discussion.UpdateDiscussionDTO;
import arguewise.demo.model.Discussion;

import java.util.List;
import java.util.Optional;

public interface IDiscussionService {
    List<Discussion> findAll();
    Optional<Discussion> findById(Long id);
    Discussion save(CreateDiscussionDTO discussion);
    Discussion update(Long id, UpdateDiscussionDTO updatedDiscussion);
    boolean deleteById(Long id);
}
