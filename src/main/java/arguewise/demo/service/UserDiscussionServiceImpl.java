package arguewise.demo.service;

import arguewise.demo.dto.Discussion.DiscussionWithUserParticipation;
import arguewise.demo.dto.userDiscussion.JoinDiscussionDTO;
import arguewise.demo.exception.ConflictingRequestException;
import arguewise.demo.exception.NotFoundException;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.Space;
import arguewise.demo.model.User;
import arguewise.demo.model.UserSpace;
import arguewise.demo.model.UsersDiscussion;
import arguewise.demo.repository.DiscussionRepository;
import arguewise.demo.repository.UserRepository;
import arguewise.demo.repository.UsersDiscussionRepository;
import arguewise.demo.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDiscussionServiceImpl implements IUserDiscussionService {

    @Autowired
    private UsersDiscussionRepository usersDiscussionRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private IUserSpaceService userSpaceService;

    @Override
    public void joinDiscussion(Long discussionId, JoinDiscussionDTO joinDiscussionDTO) {
        User user = SecurityUtils.getCurrentUser();
        Optional<Discussion> discussion = discussionRepository.findById(discussionId);

        if (discussion.isEmpty()) {
            throw new NotFoundException("Discussion not found");
        }

        Optional<UsersDiscussion> existingUsersDiscussion = usersDiscussionRepository.findByUserAndDiscussion(user, discussion.get());

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
        usersDiscussionRepository.save(usersDiscussion);
    }

    @Override
    public Page<DiscussionWithUserParticipation> findDiscussionsByUserId(Long userId, Pageable pageable) {
        Page<UsersDiscussion> usersDiscussions = usersDiscussionRepository.findByUserId(userId, pageable);

        Page<Discussion> discussions = usersDiscussions
                .map(UsersDiscussion::getDiscussion);

        return decorateDiscussions(discussions);
    }

    @Override
    public Page<DiscussionWithUserParticipation> getRecommendedUserDiscussions(Pageable pageable) {
        // TOOD - implement this
        Pageable spacePaging = PageRequest.of(0, 100);
        // Fetch all spaces the user is subscribed to
        List<UserSpace> spacesSubscribedByUser =  userSpaceService.findSpacesForCurrentUser(spacePaging).getContent();
        List<Long> spaceIds = spacesSubscribedByUser
                .stream()
                .map(UserSpace::getSpace)
                .map(Space::getId)
                .toList();
        // Fetch all discussions from these spaces
        Page<Discussion> discussions = discussionRepository
                .findBySpaceIdIn(spaceIds, pageable);

        return decorateDiscussions(discussions);
    }

    private Page<DiscussionWithUserParticipation> decorateDiscussions(Page<Discussion> discussions) {
        User user = SecurityUtils.getCurrentUser();
        List<Long> discussionIds = discussions.stream().map(Discussion::getId).toList();
        List<UsersDiscussion> userDiscussionSides = usersDiscussionRepository.findByUserAndDiscussionIdIn(user, discussionIds);

        return discussions.map(discussion -> {
            Optional<UsersDiscussion> userDiscussion = userDiscussionSides
                    .stream()
                    .filter(usersDiscussion -> usersDiscussion.getDiscussion().getId().equals(discussion.getId()))
                    .findFirst();

            UsersDiscussion.Side side = userDiscussion.map(UsersDiscussion::getSide).orElse(null);

            return new DiscussionWithUserParticipation(discussion, side, discussion.getCreator().getUsername());
        });
    }
}
