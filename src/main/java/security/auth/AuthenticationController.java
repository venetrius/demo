package security.auth;

import dto.UserCredentialsDto;
import lombok.RequiredArgsConstructor;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import repository.UserRepository;
import security.config.JwtService;


@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationService service;

    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/api/auth/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public String register(@RequestBody UserCredentialsDto userCredentialsDto) {
        String encodedPassword = passwordEncoder.encode(userCredentialsDto.getPassword());
        User user = User.builder().build();
        user.setUserName(userCredentialsDto.getUserName());
        user.setPassword(encodedPassword);
        user.setEmail(userCredentialsDto.getEmail());
        userRepository.save(user);
        return jwtService.generateToken(user);
    }
}

