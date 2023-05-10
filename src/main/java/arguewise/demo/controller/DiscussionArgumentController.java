package arguewise.demo.controller;

import arguewise.demo.dto.argument.ArgumentResponseDTO;
import arguewise.demo.dto.argument.CreateArgumentDTO;
import arguewise.demo.model.Argument;
import arguewise.demo.service.IArgumentService;
import arguewise.demo.service.IDiscussionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/discussions/{discussionId}/arguments")
public class DiscussionArgumentController {

    private static final Logger logger = LoggerFactory.getLogger(ArgumentController.class);

    @Autowired
    private IArgumentService argumentService;

    @Autowired
    private IDiscussionService discussionService;

    @GetMapping
    public ResponseEntity<List<ArgumentResponseDTO>> getAllArguments(@PathVariable Long discussionId) {
        logger.info("Received request to get all arguments for discussion with id: {}", discussionId);
        return ResponseEntity.ok(
                argumentService
                        .findAllByDiscussionId(discussionId)
                        .stream()
                        .map(argument -> new ArgumentResponseDTO(argument))
                        .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<ArgumentResponseDTO> createArgument(@PathVariable Long discussionId,
                                                              @Valid @RequestBody CreateArgumentDTO createArgumentDTO) {
        logger.info("Received request to create an argument for discussion with id: {}", discussionId);
        if (discussionService.existsById(discussionId)) {
            Argument argument = argumentService.save(discussionId, createArgumentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ArgumentResponseDTO(argument));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
