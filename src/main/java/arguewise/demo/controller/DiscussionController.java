package arguewise.demo.controller;

import arguewise.demo.dto.Discussion.CreateDiscussionDTO;
import arguewise.demo.dto.Discussion.DiscussionResponseDTO;
import arguewise.demo.dto.Discussion.UpdateDiscussionDTO;
import arguewise.demo.model.Discussion;
import arguewise.demo.service.IDiscussionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/discussions")
public class DiscussionController {

    @Autowired
    private IDiscussionService  discussionService;

    @GetMapping
    public ResponseEntity<List<DiscussionResponseDTO>> getAllDiscussions() {
        return ResponseEntity.ok(
                discussionService
                        .findAll()
                        .stream()
                        .map(discussion ->new DiscussionResponseDTO(discussion))
                        .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscussionResponseDTO> getDiscussionById(@PathVariable Long id) {
        Optional<Discussion> discussion = discussionService.findById(id);
        if (discussion.isPresent()) {
            return ResponseEntity.ok(new DiscussionResponseDTO(discussion.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DiscussionResponseDTO> createDiscussion(@Valid @RequestBody CreateDiscussionDTO createDiscussionDTO) {
        Discussion discussion = discussionService.save(createDiscussionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DiscussionResponseDTO(discussion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscussionResponseDTO> updateDiscussion(@PathVariable Long id, @Valid @RequestBody UpdateDiscussionDTO updatedDiscussion) {
        if (discussionService.findById(id).isPresent()) {
            discussionService.update(id, updatedDiscussion);
            return ResponseEntity.ok(new DiscussionResponseDTO(discussionService.update(id, updatedDiscussion)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscussion(@PathVariable Long id) {
        if (discussionService.findById(id).isPresent()) {
            discussionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

