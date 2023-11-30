package arguewise.demo.service;

import arguewise.demo.dto.Discussion.CreateDiscussionDTO;
import arguewise.demo.dto.Discussion.DiscussionWithUserParticipation;
import arguewise.demo.dto.Discussion.UpdateDiscussionDTO;
import arguewise.demo.exception.NotFoundException;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.Space;
import arguewise.demo.model.User;
import arguewise.demo.model.UsersDiscussion;
import arguewise.demo.repository.DiscussionRepository;
import arguewise.demo.repository.SpaceRepository;
import arguewise.demo.repository.UsersDiscussionRepository;
import arguewise.demo.security.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class DiscussionServiceImpl implements IDiscussionService {

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private UsersDiscussionRepository usersDiscussionRepository;

    @Override
    public Page<Discussion> findAll(Pageable pageable) {
        return discussionRepository.findAll(pageable);
    }

    @Override
    public DiscussionWithUserParticipation findById(Long id) {
        Discussion discussion = discussionRepository.findById(id).orElseThrow(() -> new NotFoundException("Discussion is not found"));
        String creatorName = discussion.getCreator().getUsername();
        return new DiscussionWithUserParticipation(discussion, getCurrentUsersSide(discussion), creatorName);
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
        if (existingDiscussion.isEmpty()) {
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

    @Override
    public boolean existsById(Long id) {
        return discussionRepository.existsById(id);
    }

    @Override
    public void isActiveOrThrow(Discussion discussion) {
        if(discussion.getStatus() != Discussion.DiscussionStatus.ACTIVE) {
            throw new IllegalArgumentException("Invalid action on Discussion with status " + discussion.getStatus());
        }
    }

    private UsersDiscussion.Side getCurrentUsersSide(Discussion discussion) {
        User user = SecurityUtils.getCurrentUser();
        if(user == null) {
            return null;
        }
        UsersDiscussion usersDiscussion = usersDiscussionRepository.findByUserAndDiscussion(user, discussion).orElse(null);
        if(usersDiscussion == null) {
            return null;
        }
        return usersDiscussion.getSide();
    }
}
