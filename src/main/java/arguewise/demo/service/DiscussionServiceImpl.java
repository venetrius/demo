package arguewise.demo.service;

import arguewise.demo.dto.Discussion.CreateDiscussionDTO;
import arguewise.demo.dto.Discussion.UpdateDiscussionDTO;
import arguewise.demo.exception.NotFoundException;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.Space;
import arguewise.demo.model.User;
import arguewise.demo.repository.DiscussionRepository;
import arguewise.demo.repository.SpaceRepository;
import arguewise.demo.security.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscussionServiceImpl implements IDiscussionService {

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Override
    public List<Discussion> findAll() {
        return discussionRepository.findAll();
    }

    @Override
    public Optional<Discussion> findById(Long id) {
        return discussionRepository.findById(id);
    }

    @Override
    public Discussion save(CreateDiscussionDTO createDiscussionDTO) {
        Optional<Space> space = spaceRepository.findById(createDiscussionDTO.getSpaceID());
        if(space.isEmpty()) {
            throw new NotFoundException("The provided space id does not reference a space");
        }
        User creator = SecurityUtils.getCurrentUser();
        Discussion discussion = new Discussion(createDiscussionDTO, creator, space.get());
        return discussionRepository.save(discussion);
    }


    @Override
    public Discussion update(Long id, UpdateDiscussionDTO updatedDiscussion) {
        Optional<Discussion> existingDiscussion = discussionRepository.findById(id);
        if (!existingDiscussion.isPresent()) {
            throw new NotFoundException("Discussion is not found");
        }
        Discussion discussion = existingDiscussion.get();

        if (updatedDiscussion.getTopic() == null && updatedDiscussion.getTimeLimit() == null) {
            throw new IllegalArgumentException("At least one field (topic or time limit) must be set for update");
        }

        if (updatedDiscussion.isUpToDate(discussion)) {
            return discussion;
        }

        if (updatedDiscussion.getTopic() != null) {
            discussion.setTopic(updatedDiscussion.getTopic());
        }
        if (updatedDiscussion.getTimeLimit() != null) {
            discussion.setTimeLimit(updatedDiscussion.getTimeLimit());
        }
        return discussionRepository.save(discussion);
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        if (discussionRepository.existsById(id)) {
            discussionRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
