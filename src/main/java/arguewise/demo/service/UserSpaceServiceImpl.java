package arguewise.demo.service;

import arguewise.demo.exception.NotFoundException;
import arguewise.demo.model.Space;
import arguewise.demo.model.User;
import arguewise.demo.model.UserSpace;
import arguewise.demo.repository.SpaceRepository;
import arguewise.demo.repository.UserSpaceRepository;
import arguewise.demo.security.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserSpaceServiceImpl implements IUserSpaceService {
    @Autowired
    private final SpaceRepository spaceRepository;

    @Autowired
    private UserSpaceRepository userSpaceRepository;

    @Override
    public void followSpace(Long spaceId) {
        User user = SecurityUtils.getCurrentUser();
        Optional<Space> space = spaceRepository.findById(spaceId);

        if (space.isEmpty()) {
            throw new NotFoundException("Space not found");
        }

        Optional<UserSpace> existingUserSpace = userSpaceRepository.findByUserAndSpace(user, space.get());
        if(existingUserSpace.isPresent()) {
            return;
        }

        UserSpace userSpace = new UserSpace(user, space.get());
        userSpaceRepository.save(userSpace);
    }

    @Override
    public Page<UserSpace> findSpacesForCurrentUser(Pageable pageable) {
        User user = SecurityUtils.getCurrentUser();
        return userSpaceRepository.findByUserId(user.getId(), pageable);
    }
}
