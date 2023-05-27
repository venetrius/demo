package arguewise.demo.service;

import arguewise.demo.model.Space;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserSpaceService {

    public void followSpace(Long discussionId);
    public List<Space> findSpacesForCurrentUser();
}
