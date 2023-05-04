package arguewise.demo.dto.userDiscussion;

import arguewise.demo.model.UsersDiscussion;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinDiscussionDTO {

    @NotNull(message = "Side is required")
    private UsersDiscussion.Side side;
}

