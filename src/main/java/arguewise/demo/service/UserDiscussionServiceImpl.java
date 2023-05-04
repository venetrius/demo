package arguewise.demo.service;

import arguewise.demo.dto.userDiscussion.JoinDiscussionDTO;
import arguewise.demo.exception.ConflictingRequestException;
import arguewise.demo.exception.NotFoundException;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.User;
import arguewise.demo.model.UsersDiscussion;
import arguewise.demo.repository.DiscussionRepository;
import arguewise.demo.repository.UserDiscussionRepository;
import arguewise.demo.repository.UserRepository;
import arguewise.demo.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDiscussionServiceImpl implements IUserDiscussionService {

    @Autowired
    private UserDiscussionRepository userDiscussionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Override
    public void joinDiscussion(Long discussionId, JoinDiscussionDTO joinDiscussionDTO) {
        User user = SecurityUtils.getCurrentUser();
        Optional<Discussion> discussion = discussionRepository.findById(discussionId);

        if (discussion.isEmpty()) {
            throw new NotFoundException("Discussion not found");
        }

        Optional<UsersDiscussion> existingUsersDiscussion = userDiscussionRepository.findByUserAndDiscussion(user, discussion.get());

        if (existingUsersDiscussion.isPresent()) {
            if (existingUsersDiscussion.get().getSide() == joinDiscussionDTO.getSide()) {
                // User has already joined the discussion with the same side, return Created
                return;
            } else {
                // User has already joined the discussion with a different side, return 409
                throw new ConflictingRequestException("User has already joined the discussion with a different side");
            }
        }

        UsersDiscussion usersDiscussion = new UsersDiscussion(user, discussion.get(), joinDiscussionDTO.getSide());
        userDiscussionRepository.save(usersDiscussion);
    }

    @Override
    public List<Discussion> findDiscussionsByUserId(Long userId) {
        List<UsersDiscussion> usersDiscussions = userDiscussionRepository.findByUserId(userId);
        return usersDiscussions.stream()
                .map(UsersDiscussion::getDiscussion)
                .collect(Collectors.toList());
    }
}
