package arguewise.demo.service;

import arguewise.demo.dto.Discussion.DiscussionWithUserParticipation;
import arguewise.demo.dto.space.SpaceResponseDTO;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.Space;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ISpaceService {
    List<Space> getAllSpaces();

    List<DiscussionWithUserParticipation> getDiscussionBySpaceId(Long id);

    Optional<Space> getSpaceById(Long id);

    Page<SpaceResponseDTO> getAllSpacesWithUserJoinInfo(Pageable pageable);

    Space createSpace(Space space);

    Optional<Space> updateSpace(Long id, Space updatedSpace);

    boolean deleteSpace(Long id);

    void likeSpace(Long id);

    void unlikeSpace(Long id);

    int getTotalLikes(Long id);

    long getSpaceCount();
}
