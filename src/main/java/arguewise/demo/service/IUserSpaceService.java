package arguewise.demo.service;

import arguewise.demo.model.UserSpace;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserSpaceService {

    void followSpace(Long discussionId);
    List<UserSpace> findSpacesForCurrentUser();
}
