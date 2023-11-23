package arguewise.demo.model;

import arguewise.demo.dto.Discussion.CreateDiscussionDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id", nullable = false)
    private Space space;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @NotBlank(message = "Topic is required")
    @Size(max = 100, message = "Topic should be no longer than 100 characters")
    private String topic;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description should be no longer than 500 characters")
    private String description;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @NotNull(message = "Time limit is required")
    private LocalDateTime timeLimit;

    @NotNull(message = "Discussion status is required")
    @Enumerated(EnumType.STRING)
    private DiscussionStatus status = DiscussionStatus.ACTIVE;

    public boolean isActive() {
        return this.status == DiscussionStatus.ACTIVE;
    }

    public enum DiscussionStatus {
        ACTIVE,
        COMPLETED
    }

    public Discussion(CreateDiscussionDTO dto, User creator, Space space) {
        this.space = space;
        this.creator = creator;
        if(dto == null) {
            this.topic = "issue";
            this.timeLimit = LocalDateTime.now();
        } else {
            this.topic = dto.getTopic();
            this.description = dto.getDescription();
            this.timeLimit = dto.getTimeLimit();
        }

        this.status = DiscussionStatus.ACTIVE;
    }
}
