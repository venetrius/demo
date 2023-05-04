package arguewise.demo.controller;

import arguewise.demo.dto.userDiscussion.JoinDiscussionDTO;
import arguewise.demo.service.IUserDiscussionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/discussions")
public class UserDiscussionController {

    @Autowired
    private IUserDiscussionService userDiscussionService;

    @PostMapping("/{id}/join")
    public ResponseEntity<Void> joinDiscussion(@PathVariable Long id, @Valid @RequestBody JoinDiscussionDTO joinDiscussionDTO) {
        userDiscussionService.joinDiscussion(id, joinDiscussionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
