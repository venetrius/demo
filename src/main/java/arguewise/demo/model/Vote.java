package arguewise.demo.model;

import arguewise.demo.types.EntityType;
import arguewise.demo.types.VoteType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "votes", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "entity_id", "entity_type"}))
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Column(name = "entity_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityType entityType;

    @Column(nullable = false)
    private VoteType voteType;

    public Vote(User user, Long entityId, EntityType entityType, VoteType voteType) {
        this.user = user;
        this.entityId = entityId;
        this.entityType = entityType;
        this.voteType = voteType;
    }
}

