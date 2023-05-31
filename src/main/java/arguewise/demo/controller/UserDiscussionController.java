package arguewise.demo.controller;

import arguewise.demo.dto.Discussion.DiscussionResponseDTO;
import arguewise.demo.dto.userDiscussion.JoinDiscussionDTO;
import arguewise.demo.model.User;
import arguewise.demo.security.utils.SecurityUtils;
import arguewise.demo.service.IUserDiscussionService;
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
    public ResponseEntity<List<DiscussionResponseDTO>> getUserDiscussions() {
        User user = SecurityUtils.getCurrentUser();
        List<DiscussionResponseDTO> discussions = userDiscussionService.findDiscussionsByUserId((long) user.getId()).stream()
                .map(discussion ->new DiscussionResponseDTO(discussion))
                .collect(Collectors.toList()); //TODO user id to long
        return new ResponseEntity<>(discussions, HttpStatus.OK);
    }

    @GetMapping("/discussions/recommendations")
    public ResponseEntity<List<DiscussionResponseDTO>> getRecommendedUserDiscussions() {
        User user = SecurityUtils.getCurrentUser();
        List<DiscussionResponseDTO> discussions = userDiscussionService.getRecommendedUserDiscussions()
                .stream()
                .map(DiscussionResponseDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(discussions, HttpStatus.OK);
    }


}
