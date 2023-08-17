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
    private String biography;
    private String interests;
    private String profilePicture;
    private boolean receiveNotifications;

    public UserDTO(User user) {
        this.id = user.getId();
        this.userName = user.getUsername();
        this.email = user.getEmail();
        this.biography = user.getBiography();
        this.interests = user.getInterests();
        this.profilePicture = user.getProfilePicture();
        this.receiveNotifications = false;
    }
}
