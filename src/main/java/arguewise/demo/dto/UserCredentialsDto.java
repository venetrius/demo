package arguewise.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCredentialsDto {
    public String userName;
    public  String password;
    public String email;
}
