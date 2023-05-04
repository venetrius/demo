package arguewise.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users_discussions",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "discussion_id"})})
public class UsersDiscussion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "discussion_id", nullable = false)
    private Discussion discussion;

    @NotNull(message = "Side is required")
    @Enumerated(EnumType.STRING)
    private Side side;

    public enum Side {
        PRO,
        CONTRA
    }

    public UsersDiscussion(User user, Discussion discussion, Side side) {
        this.user = user;
        this.discussion = discussion;
        this.side = side;
    }
}

