package arguewise.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "suggestions", indexes = @Index(columnList = "type, status"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Suggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "argument_id", nullable = false)
    private Argument argument;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private SuggestionType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "varchar(32) default 'ACTIVE'")
    private SuggestionStatus status;

    private String section;
    private Integer position;
    private String text;
    private String comment;
    private Integer argumentVersion;

    @CreationTimestamp
    private LocalDateTime createdTimestamp;

    public boolean isActive() {
        return this.argument.isActive();
    }

    // Getters, setters, and constructors
    public enum SuggestionType {
        ADDITION,
        REVISION,
        REORGANIZATION
    }

    public enum SuggestionStatus {
        ACTIVE,
        ACCEPTED,
        REJECTED
    }
}
