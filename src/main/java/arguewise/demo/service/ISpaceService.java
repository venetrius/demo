package arguewise.demo.service;

import arguewise.demo.model.Discussion;
import arguewise.demo.model.Space;

import java.util.List;
import java.util.Optional;

public interface ISpaceService {
    List<Space> getAllSpaces();

    List<Discussion> getDiscussionBySpaceId(Long id);

    Optional<Space> getSpaceById(Long id);

    Space createSpace(Space space);

    Optional<Space> updateSpace(Long id, Space updatedSpace);

    boolean deleteSpace(Long id);

    void likeSpace(Long id);

    void unlikeSpace(Long id);

    int getTotalLikes(Long id);
}
