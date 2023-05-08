package arguewise.demo.service;

import arguewise.demo.dto.argument.CreateArgumentDTO;
import arguewise.demo.dto.argument.UpdateArgumentDTO;
import arguewise.demo.exception.NotFoundException;
import arguewise.demo.model.Argument;
import arguewise.demo.model.ArgumentDetail;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.User;
import arguewise.demo.repository.ArgumentRepository;
import arguewise.demo.repository.DiscussionRepository;
import arguewise.demo.security.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ArgumentServiceImpl implements IArgumentService {
    @Autowired
    private ArgumentRepository argumentRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Override
    public Optional<Argument> findById(Long id) {
        return argumentRepository.findById(id);
    }

    @Override
    public Argument save(Long discussionId, CreateArgumentDTO createArgumentDTO) {
        Optional<Discussion> discussion = discussionRepository.findById(discussionId);
        if (discussion.isEmpty()) {
            throw new NotFoundException("The provided discussion id does not reference a discussion");
        }
        User creator = SecurityUtils.getCurrentUser();
        Argument argument = new Argument(createArgumentDTO, creator, discussion.get());
        return argumentRepository.save(argument);
    }

    @Transactional
    public Argument update(Long id, UpdateArgumentDTO updatedArgument) {
        Optional<Argument> existingArgument = argumentRepository.findById(id);
        if (existingArgument.isEmpty()) {
            throw new NotFoundException("Argument is not found");
        }

        Argument argument = existingArgument.get();

        if (updatedArgument.getTitle() != null) {
            argument.setTitle(updatedArgument.getTitle());
        }
        if (updatedArgument.getArgumentDetails() != null) {
            argument.removeAllArgumentDetails();

            for (int i = 0; i < updatedArgument.getArgumentDetails().size(); i++) {
                ArgumentDetail detail = new ArgumentDetail();
                detail.setPosition(i + 1);
                detail.setText(updatedArgument.getArgumentDetails().get(i));
                detail.setArgument(argument);
                argument.addArgumentDetail(detail);
            }
        }
        return argumentRepository.save(argument);
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        if (argumentRepository.existsById(id)) {
            argumentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Collection<Argument> findAllByDiscussionId(Long discussionId) {
        return argumentRepository.findAllByDiscussionId(discussionId);
    }

}
