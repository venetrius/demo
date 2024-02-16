package arguewise.demo.service;

import arguewise.demo.model.*;
import arguewise.demo.repository.VoteRepository;
import arguewise.demo.security.utils.SecurityUtils;
import arguewise.demo.types.EntityType;
import arguewise.demo.types.VoteType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    public void castVote(Long entityId, EntityType entityType, VoteType voteType) {
        User currentUser = SecurityUtils.getCurrentUser();
        long userId = currentUser.getId();
        castVoteInternal(entityId ,entityType, voteType, currentUser);
    }

    public void castVoteInternal(Long entityId, EntityType entityType, VoteType voteType, User user) {
        long userId = user.getId();
        Optional<Vote> existingVote = voteRepository.findByUserIdAndEntityIdAndEntityType(userId, entityId, entityType);

        if (existingVote.isPresent()) {
            // Update vote if exist
            Vote vote = existingVote.get();
            vote.setVoteType(voteType);
            voteRepository.save(vote);
        } else {
            Vote vote = new Vote(user, entityId, entityType, voteType);
            voteRepository.save(vote);
        }
    }

    public List<Object[]> getNumberOfVotesForEntities(EntityType entityType, List<Long> entityIds, VoteType voteType) {
        return voteRepository.findVoteCountsForEntity(entityIds, entityType, voteType);
    }

    public long getNumberOfVoteForEntityTypeAndUser(EntityType entityType, User user) {
        return voteRepository.countByEntityTypeAndUser(entityType, user);
    }

    public List<Long> findLikedByUserForEntities(Long userId, List<Long> entityIds, EntityType entityType, VoteType voteType) {
        return voteRepository.findLikedByUserForEntities(userId, entityIds, entityType, voteType);
    }

    public List<Long> findIdForForEntityTypeAndUser(EntityType entityType, User user) {
        return voteRepository.findIdForEntityTypeAndUser(entityType, user);
    }

    public void deleteVote(Long entityID, EntityType entityType) {
        User currentUser = SecurityUtils.getCurrentUser();
        long userId = currentUser.getId();
        Optional<Vote> existingVote = voteRepository.findByUserIdAndEntityIdAndEntityType(userId, entityID, entityType);

        if (existingVote.isPresent()) {
            Vote vote = existingVote.get();
            voteRepository.delete(vote);
        }
    }
}

