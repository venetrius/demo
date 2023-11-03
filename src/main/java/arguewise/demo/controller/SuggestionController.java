package arguewise.demo.controller;

import arguewise.demo.dto.suggestion.CreateSuggestionDTO;
import arguewise.demo.dto.suggestion.SuggestionResponseDTO;
import arguewise.demo.model.Suggestion;
import arguewise.demo.service.ISuggestionService;
import arguewise.demo.service.SuggestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.attribute.UserPrincipal;

@RestController
@RequestMapping("api/arguments/{argumentId}/suggestions")
public class SuggestionController {

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
}
