package arguewise.demo.security.auth;


import arguewise.demo.dto.UserCredentialsDto;
import arguewise.demo.service.IEmailConfirmationListener;
import arguewise.demo.service.IEmailConfirmationSender;
import lombok.RequiredArgsConstructor;
import arguewise.demo.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import arguewise.demo.repository.UserRepository;
import arguewise.demo.security.config.JwtService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserRepository repository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final IEmailConfirmationSender emailConfirmationSender;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = repository.findByEmail(request.getEmail()).get();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse register(UserCredentialsDto userCredentialsDto) {
        String encodedPassword = passwordEncoder.encode(userCredentialsDto.getPassword());
        User user = User.builder().build();
        user.setUserName(userCredentialsDto.getUserName());
        user.setPassword(encodedPassword);
        user.setEmail(userCredentialsDto.getEmail());
        repository.save(user);

        emailConfirmationSender.sendEmailConfirmation(user.getEmail());

        return AuthenticationResponse.builder().token(jwtService.generateToken(user)).build();
    }
}
