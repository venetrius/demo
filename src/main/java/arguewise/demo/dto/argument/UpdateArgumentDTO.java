package arguewise.demo.dto.argument;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateArgumentDTO {
    @Size(max = 100, message = "Title should be no longer than 100 characters")
    private String title;
    private String argumentDetails;
}
