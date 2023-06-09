package arguewise.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import arguewise.demo.model.User;

@Data
@NoArgsConstructor
public class UserDTO {
    private int id;

    private String userName;
    private String email;

    public UserDTO(User user) {
        this.id = user.getId();
        this.userName = user.getUsername();
        this.email = user.getEmail();
    }
}
