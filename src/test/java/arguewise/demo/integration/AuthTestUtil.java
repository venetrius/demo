package arguewise.demo.integration;

import arguewise.demo.dto.UserCredentialsDto;
import arguewise.demo.model.User;
import arguewise.demo.repository.UserRepository;
import arguewise.demo.security.auth.AuthenticationRequest;
import arguewise.demo.security.auth.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthTestUtil {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    public String registerAndLogin(String username, String password, String email, int port) {
        UserCredentialsDto userCredentialsDto = new UserCredentialsDto(username, password, email);
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, password);
        String registrationUrl = "http://localhost:" + port + "/api/auth/register";
        restTemplate.postForEntity(registrationUrl, userCredentialsDto, String.class);
        String authenticationUrl = "http://localhost:" + port + "/api/auth/authenticate";
        ResponseEntity<AuthenticationResponse> responseEntity = restTemplate.postForEntity(authenticationUrl, authenticationRequest, AuthenticationResponse.class);

        return "Bearer " + responseEntity.getBody().getToken();
    }

    public HttpHeaders createAuthorizationHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        return headers;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }
}

