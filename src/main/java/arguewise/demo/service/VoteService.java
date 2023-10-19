package arguewise.demo.service;

import arguewise.demo.model.*;
import arguewise.demo.repository.VoteRepository;
import arguewise.demo.security.utils.SecurityUtils;
import arguewise.demo.types.EntityType;
import arguewise.demo.types.VoteType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    public void castVote(Long entityId, EntityType entityType, VoteType voteType) {
        User currentUser = SecurityUtils.getCurrentUser();
        long userId = currentUser.getId();
        Optional<Vote> existingVote = voteRepository.findByUserIdAndEntityIdAndEntityType(userId, entityId, entityType);

        if (existingVote.isPresent()) {
            // Update vote if exist
            Vote vote = existingVote.get();
            vote.setVoteType(voteType);
            voteRepository.save(vote);
        } else {
            Vote vote = new Vote(currentUser, entityId, entityType, voteType);
            voteRepository.save(vote);
        }
    }
}

