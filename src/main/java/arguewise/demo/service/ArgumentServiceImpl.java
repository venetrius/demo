package arguewise.demo.service;

import arguewise.demo.dto.argument.CreateArgumentDTO;
import arguewise.demo.dto.argument.UpdateArgumentDTO;
import arguewise.demo.exception.NotFoundException;
import arguewise.demo.model.*;
import arguewise.demo.repository.ArgumentRepository;
import arguewise.demo.repository.DiscussionRepository;
import arguewise.demo.repository.UserDiscussionRepository;
import arguewise.demo.security.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ArgumentServiceImpl implements IArgumentService {
    @Autowired
    private ArgumentRepository argumentRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private IDiscussionService discussionService;

    @Autowired
    private UserDiscussionRepository userDiscussionRepository;

    @Override
    public Optional<Argument> findById(Long id) {
        return argumentRepository.findById(id);
    }

    @Transactional
    public Argument update(Long id, UpdateArgumentDTO updatedArgument) {
        Optional<Argument> existingArgument = argumentRepository.findById(id);
        if (existingArgument.isEmpty()) {
            throw new NotFoundException("Argument is not found");
        }
        Argument argument = existingArgument.get();

        isUserRecordOrThrow(argument);
        discussionService.isActiveOrThrow(argument.getDiscussion());

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
        Optional<Argument> argument = argumentRepository.findById(id);
        if(argument.isEmpty()) {
            return false;
        }

        isUserRecordOrThrow(argument.get());
        discussionService.isActiveOrThrow(argument.get().getDiscussion());

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

    @Override
    public Argument createArgument(Long discussionId, CreateArgumentDTO createArgumentDTO) {
        User creator = SecurityUtils.getCurrentUser();
        Optional<UsersDiscussion> usersDiscussion =
                userDiscussionRepository.findByUserIdAndDiscussionId(creator.getId(), discussionId);
        if(usersDiscussion.isEmpty()) {
            throw new IllegalArgumentException("Can only create an Argument for a Discussion if joined");
        }

        discussionService.isActiveOrThrow(usersDiscussion.get().getDiscussion());

        Argument newArgument = new Argument(createArgumentDTO, creator, usersDiscussion.get().getDiscussion());
        return argumentRepository.save(newArgument);
    }

    private void isUserRecordOrThrow(Argument argument) {
        User user = SecurityUtils.getCurrentUser();

        if(! (user.getId() == argument.getAuthor().getId())) {
            throw new IllegalArgumentException("You can only delete Arguments you created");
        }
    }

}
