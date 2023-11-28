package arguewise.demo.controller;

import arguewise.demo.dto.space.SpaceResponseDTO;
import arguewise.demo.service.IUserSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/me/spaces")
public class UserSpaceController {

    @Autowired
    private IUserSpaceService userSpaceService;

    @PostMapping("/{spaceId}")
    public ResponseEntity<Void> followSpace(@PathVariable Long spaceId) {
        userSpaceService.followSpace(spaceId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<SpaceResponseDTO>> getUserSpaces(Pageable pageable) {
        Page<SpaceResponseDTO> spaces = userSpaceService.findSpacesForCurrentUser(pageable).map(SpaceResponseDTO::new);
        return new ResponseEntity<>(spaces, HttpStatus.OK);
    }
}