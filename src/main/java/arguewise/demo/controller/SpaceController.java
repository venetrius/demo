package arguewise.demo.controller;

import arguewise.demo.dto.Discussion.DiscussionResponseDTO;
import arguewise.demo.dto.space.SpaceResponseDTO;
import arguewise.demo.model.Space;
import arguewise.demo.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/spaces")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @GetMapping
    public ResponseEntity<List<SpaceResponseDTO>> getAllSpaces() {
        return ResponseEntity.ok(spaceService.getAllSpacesWithUserJoinInfo());
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
                .map(discussion -> new DiscussionResponseDTO(discussion))
                .collect(Collectors.toList());
    }
}
