package arguewise.demo.controller;

import arguewise.demo.dto.Discussion.CreateDiscussionDTO;
import arguewise.demo.dto.Discussion.DiscussionResponseDTO;
import arguewise.demo.dto.Discussion.DiscussionWithUserParticipation;
import arguewise.demo.dto.Discussion.UpdateDiscussionDTO;
import arguewise.demo.model.Discussion;
import arguewise.demo.service.IDiscussionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/discussions")
public class DiscussionController {

    private static final Logger logger = LoggerFactory.getLogger(DiscussionController.class);

    @Autowired
    private IDiscussionService  discussionService;

    @GetMapping
    public ResponseEntity<List<DiscussionResponseDTO>> getAllDiscussions(Pageable pageable) {
        logger.info("Received request to get all discussions");
        return ResponseEntity.ok(
                discussionService
                        .findAll(pageable)
                        .stream()
                        .map(DiscussionResponseDTO::new)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscussionResponseDTO> getDiscussionById(@PathVariable Long id) {
        logger.info("Received request to get discussion by id: {}", id);
        DiscussionWithUserParticipation discussionWithUserParticipation = discussionService.findById(id);
        return ResponseEntity.ok(new DiscussionResponseDTO(discussionWithUserParticipation));
    }

    @PostMapping
    public ResponseEntity<DiscussionResponseDTO> createDiscussion(@Valid @RequestBody CreateDiscussionDTO createDiscussionDTO) {
        logger.info("Received request to create a discussion");
        Discussion discussion = discussionService.save(createDiscussionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DiscussionResponseDTO(discussion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscussionResponseDTO> updateDiscussion(@PathVariable Long id, @Valid @RequestBody UpdateDiscussionDTO updatedDiscussion) {
        logger.info("Received request to update discussion with id: {}", id);
        if (discussionService.existsById(id)) {
            discussionService.update(id, updatedDiscussion);
            return ResponseEntity.ok(new DiscussionResponseDTO(discussionService.update(id, updatedDiscussion)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscussion(@PathVariable Long id) {
        logger.info("Received request to delete discussion with id: {}", id);
        if (discussionService.existsById(id)) {
            discussionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

