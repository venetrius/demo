package arguewise.demo.service;

import arguewise.demo.dto.Discussion.DiscussionWithUserParticipation;
import arguewise.demo.dto.space.SpaceResponseDTO;
import arguewise.demo.dto.space.SpaceStatisticsDTO;
import arguewise.demo.exception.NotFoundException;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.Space;
import arguewise.demo.model.User;
import arguewise.demo.model.UserSpace;
import arguewise.demo.model.UsersDiscussion;
import arguewise.demo.repository.DiscussionRepository;
import arguewise.demo.repository.SpaceRepository;
import arguewise.demo.repository.UserSpaceRepository;
import arguewise.demo.repository.UsersDiscussionRepository;
import arguewise.demo.security.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SpaceService implements ISpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private UserSpaceRepository userSpaceRepository;

    @Autowired
    private UsersDiscussionRepository usersDiscussionRepository;

    @Override
    public Page<SpaceResponseDTO> getAllSpacesWithUserJoinInfo(Pageable pageable) {
        User user = SecurityUtils.getCurrentUser();
        Map<Long, UserSpace> userSpacesMap = new HashMap<>();

        assert user != null;
        List<UserSpace> userSpaces = userSpaceRepository.findByUserId(user.getId());
        userSpaces.forEach(userSpace -> userSpacesMap.put(userSpace.getSpace().getId(), userSpace));

        return spaceRepository.findAll(pageable).map(space -> {
            SpaceStatisticsDTO spaceStatisticsDTO = getSpaceStatisticDTO(space);
            SpaceResponseDTO dto = new SpaceResponseDTO(space, spaceStatisticsDTO);
            if (userSpacesMap.containsKey(space.getId())) {
                dto = new SpaceResponseDTO(userSpacesMap.get(space.getId()), spaceStatisticsDTO);
            }
            return dto;
        });
    }

    private SpaceStatisticsDTO getSpaceStatisticDTO(Space space) {
        return new SpaceStatisticsDTO(space.getTotalLikes(), 0L, 0L);
    }

    @Override
    public List<Space> getAllSpaces() {
        return spaceRepository.findAll();
    }

    @Override
    public List<DiscussionWithUserParticipation> getDiscussionBySpaceId(Long id) {
        Optional<Space> space = spaceRepository.findById(id);
        if (space.isEmpty()) {
            throw new NotFoundException("Space with id " + id + " not found");
        }

        User user = SecurityUtils.getCurrentUser();


        List<Discussion> discussions = space.get().getDiscussions();
        // TODO DRY used in multiple places
        List<Long> discussionIds = discussions.stream().map(Discussion::getId).toList();
        List<UsersDiscussion> userDiscussionSides = usersDiscussionRepository.findByUserAndDiscussionIdIn(user, discussionIds);

        return discussions.stream().map(discussion -> {
            UsersDiscussion.Side side = userDiscussionSides.stream()
                    .filter(userDiscussionSide -> userDiscussionSide.getDiscussion().getId().equals(discussion.getId()))
                    .findFirst()
                    .map(UsersDiscussion::getSide)
                    .orElse(null);
            return new DiscussionWithUserParticipation(discussion, side, discussion.getCreator().getUsername());
        }).toList();
    }

    @Override
    public Optional<Space> getSpaceById(Long id) {
        return spaceRepository.findById(id);
    }

    @Override
    public Space createSpace(Space space) {
        return spaceRepository.save(space);
    }

    @Override
    public Optional<Space> updateSpace(Long id, Space updatedSpace) {
        return spaceRepository.findById(id).map(space -> {
            space.setName(updatedSpace.getName());
            space.setDescription(updatedSpace.getDescription());
            return spaceRepository.save(space);
        });
    }

    @Transactional
    @Override
    public boolean deleteSpace(Long id) {
        if (spaceRepository.existsById(id)) {
            spaceRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void likeSpace(Long id) {
        Optional<Space> space = spaceRepository.findById(id);
        if (space.isEmpty()) {
            throw new NotFoundException("Space is not found");
        }

        User user = SecurityUtils.getCurrentUser();
        space.get().like(user);
        spaceRepository.save(space.get());
    }

    @Override
    public void unlikeSpace(Long id) {
        Optional<Space> space = spaceRepository.findById(id);
        if (space.isEmpty()) {
            throw new NotFoundException("Space is not found");
        }

        User user = SecurityUtils.getCurrentUser();
        space.get().unlike(user);
        spaceRepository.save(space.get());
    }

    @Override
    public int getTotalLikes(Long id) {
        Optional<Space> space = spaceRepository.findById(id);
        if (space.isEmpty()) {
            throw new NotFoundException("Space is not found");
        }
        return space.get().getTotalLikes();
    }

    @Override
    public long getSpaceCount() {
        return spaceRepository.count();
    }
}
