package arguewise.demo.service;

import arguewise.demo.dto.argument.CreateArgumentDTO;
import arguewise.demo.dto.argument.UpdateArgumentDTO;
import arguewise.demo.model.Argument;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public interface IArgumentService {
    Optional<Argument> findById(Long id);
    Argument save(Long discussionId, CreateArgumentDTO createArgumentDTO);
    Argument update(Long id, UpdateArgumentDTO updatedArgument);
    boolean deleteById(Long id);

    Collection<Argument> findAllByDiscussionId(Long discussionId);
}

