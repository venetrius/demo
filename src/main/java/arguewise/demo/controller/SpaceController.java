package arguewise.demo.controller;

import arguewise.demo.dto.Discussion.DiscussionResponseDTO;
import arguewise.demo.dto.space.SpaceResponseDTO;
import arguewise.demo.model.Space;
import arguewise.demo.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/spaces")
public class SpaceController {

    // TODO should reference the service interface
    @Autowired
    private SpaceService spaceService;

    @GetMapping
    public ResponseEntity<Page<SpaceResponseDTO>> getAllSpaces(Pageable pageable) {
        Page<SpaceResponseDTO> page = spaceService.getAllSpacesWithUserJoinInfo(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Space> getSpaceById(@PathVariable Long id) {
        return spaceService.getSpaceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Space> createSpace(@Valid @RequestBody Space space) {
        return ResponseEntity.status(HttpStatus.CREATED).body(spaceService.createSpace(space));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Space> updateSpace(@PathVariable Long id, @Valid @RequestBody Space updatedSpace) {
        return spaceService.updateSpace(id, updatedSpace)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpace(@PathVariable Long id) {
        return spaceService.deleteSpace(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/discussions")
    public List<DiscussionResponseDTO> getSpaceDiscussions(@PathVariable Long id) {
        return spaceService.getDiscussionBySpaceId(id)
                .stream()
                .map(DiscussionResponseDTO::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}/like")
    public ResponseEntity<Void> likeSpace(@PathVariable Long id) {
        spaceService.likeSpace(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/unlike")
    public ResponseEntity<Void> unlikeSpace(@PathVariable Long id) {
        spaceService.unlikeSpace(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<Integer> getTotalLikes(@PathVariable Long id) {
        int totalLikes = spaceService.getTotalLikes(id);
        return ResponseEntity.ok(totalLikes);
    }
}
