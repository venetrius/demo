package arguewise.demo.controller;

import arguewise.demo.dto.UserDTO;
import arguewise.demo.model.User;
import arguewise.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/profile")
@AllArgsConstructor
public class UserController {

    @Autowired
    private final UserRepository userRepository;
    @GetMapping
    public UserDTO getUserInfo() {
        User currrentUser = userRepository.findByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).get();
        return new UserDTO(currrentUser);
    }
}
