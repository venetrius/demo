package arguewise.demo.model;

import arguewise.demo.dto.argument.CreateArgumentDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "arguments")
public class Argument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "discussion_id", nullable = false)
    private Discussion discussion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title should be no longer than 100 characters")
    private String title;

    @NotBlank(message = "Argument details are required")
    private String argumentDetails;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    public Argument(CreateArgumentDTO dto, User author, Discussion discussion) {
        this.discussion = discussion;
        this.author = author;
        this.title = dto.getTitle();
        this.argumentDetails = dto.getArgumentDetails();
    }
}

