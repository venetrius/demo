package arguewise.demo.repository;

import arguewise.demo.model.Vote;
import arguewise.demo.types.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByUserIdAndEntityIdAndEntityType(Long userId, Long entityId, EntityType entityType);
}
