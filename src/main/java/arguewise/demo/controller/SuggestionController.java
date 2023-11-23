package arguewise.demo.controller;

import arguewise.demo.dto.argument.PutArgumentVoteDTO;
import arguewise.demo.dto.suggestion.CreateSuggestionDTO;
import arguewise.demo.dto.suggestion.SuggestionResponseDTO;
import arguewise.demo.model.Argument;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.Suggestion;
import arguewise.demo.service.ISuggestionService;
import arguewise.demo.service.SuggestionService;
import arguewise.demo.types.VoteType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

@RestController
@RequestMapping("api/arguments/{argumentId}/suggestions")
public class SuggestionController {

    private static final Logger logger = LoggerFactory.getLogger(ArgumentController.class);
    private final ISuggestionService suggestionService;

    public SuggestionController(ISuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @PostMapping
    public ResponseEntity<SuggestionResponseDTO> createSuggestion(@RequestBody CreateSuggestionDTO dto,
                                                       @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Suggestion suggestion = suggestionService.createSuggestion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuggestionResponseDTO(suggestion));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuggestionResponseDTO> getSuggestion(@PathVariable Long id) {
        Suggestion suggestion = suggestionService.getSuggestion(id);
        return ResponseEntity.ok(new SuggestionResponseDTO(suggestion));
    }

    @GetMapping
    public ResponseEntity<List<SuggestionResponseDTO>> getSuggestions(@PathVariable Long argumentId) {
        List<Suggestion> suggestions = suggestionService.getSuggestionBy(argumentId);
        return ResponseEntity.ok(suggestions.stream().map(SuggestionResponseDTO::new).toList());
    }

    @PutMapping("/{id}/vote")
    public ResponseEntity<Boolean> castVote(@PathVariable Long id, @RequestBody PutArgumentVoteDTO inputData) {
        logger.info("Received request to cast vote on argument with id: {}", id);
        Suggestion suggestion = suggestionService.findById(id).orElse(null);

        if(suggestion == null) {
            return ResponseEntity.notFound().build();
        }

        if(!suggestion.isActive()) {
            return ResponseEntity.badRequest().build();
        }
        VoteType validated = VoteType.valueOf(inputData.getVoteType().toUpperCase());
        suggestionService.vote(id, validated);
        return ResponseEntity.ok(true);
    }
}
