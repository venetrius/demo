package arguewise.demo.dto.argument;

import arguewise.demo.model.Argument;
import arguewise.demo.model.UsersDiscussion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateArgumentDTO {
    private long discussionID;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title should be no longer than 100 characters")
    private String title;

    @NotBlank(message = "Argument details are required")
    private String argumentDetails;
}
