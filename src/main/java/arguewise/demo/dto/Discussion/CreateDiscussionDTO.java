package arguewise.demo.dto.Discussion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDiscussionDTO {
    private long spaceID;

    @NotBlank(message = "Topic is required")
    @Size(max = 100, message = "Topic should be no longer than 100 characters")
    private String topic;

    @NotNull(message = "Time limit is required")
    private LocalDateTime timeLimit;

}
