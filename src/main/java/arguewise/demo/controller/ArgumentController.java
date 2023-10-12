package arguewise.demo.controller;

import arguewise.demo.dto.argument.ArgumentResponseDTO;
import arguewise.demo.dto.argument.UpdateArgumentDTO;
import arguewise.demo.model.Argument;
import arguewise.demo.model.Discussion;
import arguewise.demo.service.IArgumentService;
import arguewise.demo.service.IDiscussionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/arguments") // TODO improve mapping, root should not be api in this class

public class ArgumentController {

    private static final Logger logger = LoggerFactory.getLogger(ArgumentController.class);

    @Autowired
    private IArgumentService argumentService;

    @Autowired
    private IDiscussionService discussionService;

    @GetMapping("/{id}")
    public ResponseEntity<ArgumentResponseDTO> getArgumentById(@PathVariable Long id) {
        logger.info("Received request to get argument by id: {}", id);
        Optional<Argument> argument = argumentService.findById(id);
        if (argument.isPresent()) {
            return ResponseEntity.ok(new ArgumentResponseDTO(argument.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArgumentResponseDTO> updateArgument(@PathVariable Long id, @Valid @RequestBody UpdateArgumentDTO updatedArgument) {
        logger.info("Received request to update argument with id: {}", id);
        Argument argument = argumentService.findById(id).orElse(null);
        if(argument == null) {
            return ResponseEntity.notFound().build();
        }

        if(argument.getDiscussion().getStatus() != Discussion.DiscussionStatus.ACTIVE) {
            return ResponseEntity.badRequest().build();
        }

        argumentService.update(id, updatedArgument);
        return ResponseEntity.ok(new ArgumentResponseDTO(argumentService.update(id, updatedArgument)));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArgument(@PathVariable Long id) {
        logger.info("Received request to delete argument with id: {}", id);
        if (argumentService.findById(id).isPresent()) {
            argumentService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
