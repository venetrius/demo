package arguewise.demo.model;

import arguewise.demo.dto.CreateDiscussionDTO;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "discussions")
public class Discussion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "space_id", nullable = false)
    private Space space;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @NotBlank(message = "Topic is required")
    @Size(max = 100, message = "Topic should be no longer than 100 characters")
    private String topic;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @NotNull(message = "Time limit is required")
    private LocalDateTime timeLimit;

    @NotNull(message = "Discussion status is required")
    @Enumerated(EnumType.STRING)
    private DiscussionStatus status;

    public enum DiscussionStatus {
        ACTIVE,
        COMPLETED
    }

}
