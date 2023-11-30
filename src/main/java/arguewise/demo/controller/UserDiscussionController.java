package arguewise.demo.controller;

import arguewise.demo.dto.Discussion.DiscussionResponseDTO;
import arguewise.demo.dto.userDiscussion.JoinDiscussionDTO;
import arguewise.demo.model.User;
import arguewise.demo.security.utils.SecurityUtils;
import arguewise.demo.service.IUserDiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/me")
public class UserDiscussionController {

    @Autowired
    private IUserDiscussionService userDiscussionService;

    @PostMapping("/discussions/{discussionId}/join")
    public ResponseEntity<Void> joinDiscussion(@PathVariable Long discussionId, @RequestBody JoinDiscussionDTO joinDiscussionDTO) {
        userDiscussionService.joinDiscussion(discussionId, joinDiscussionDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/discussions")
    public ResponseEntity<Page<DiscussionResponseDTO>> getUserDiscussions(Pageable pageable) {
        User user = SecurityUtils.getCurrentUser();
        //TODO user id to long
        Page<DiscussionResponseDTO> discussions = userDiscussionService
                .findDiscussionsByUserId((long) user.getId(), pageable)
                .map(DiscussionResponseDTO::new);
        return new ResponseEntity<>(discussions, HttpStatus.OK);
    }

    @GetMapping("/discussions/recommendations")
    public ResponseEntity<Page<DiscussionResponseDTO>> getRecommendedUserDiscussions(Pageable pageable) {
        User user = SecurityUtils.getCurrentUser();
        Page<DiscussionResponseDTO> discussions = userDiscussionService
                .getRecommendedUserDiscussions(pageable)
                .map(DiscussionResponseDTO::new);
        return new ResponseEntity<>(discussions, HttpStatus.OK);
    }


}
