package arguewise.demo.service;

import arguewise.demo.domain.ArgumentDetails;
import arguewise.demo.dto.argument.CreateArgumentDTO;
import arguewise.demo.dto.argument.UpdateArgumentDTO;
import arguewise.demo.model.Argument;
import arguewise.demo.model.UsersDiscussion;
import arguewise.demo.types.VoteType;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public interface IArgumentService {
    Optional<Argument> findById(Long id);
    Argument update(Long id, UpdateArgumentDTO updatedArgument);
    boolean deleteById(Long id);
    Collection<ArgumentDetails> findAllByDiscussionId(Long discussionId);
    Argument createArgument(Long discussionId, CreateArgumentDTO createArgumentDTO);
    void voteOnArgument(Long argumentId, VoteType voteType);
    UsersDiscussion.Side getArgumentSide(Argument argument);
}

