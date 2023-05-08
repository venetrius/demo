package arguewise.demo.controller;

import arguewise.demo.dto.suggestion.CreateSuggestionDTO;
import arguewise.demo.dto.suggestion.SuggestionResponseDTO;
import arguewise.demo.model.Suggestion;
import arguewise.demo.service.SuggestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.attribute.UserPrincipal;

@RestController
@RequestMapping("/api/suggestions")
public class SuggestionController {

    private final SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @PostMapping
    public ResponseEntity<SuggestionResponseDTO> createSuggestion(@RequestBody CreateSuggestionDTO dto,
                                                       @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Suggestion suggestion = suggestionService.createSuggestion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuggestionResponseDTO(suggestion));
    }
}
