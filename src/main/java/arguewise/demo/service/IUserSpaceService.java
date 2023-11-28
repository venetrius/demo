package arguewise.demo.service;

import arguewise.demo.model.UserSpace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserSpaceService {

    void followSpace(Long discussionId);
    Page<UserSpace> findSpacesForCurrentUser(Pageable pageable);
}
