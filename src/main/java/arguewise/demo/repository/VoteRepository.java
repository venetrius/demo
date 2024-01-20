package arguewise.demo.repository;

import arguewise.demo.model.User;
import arguewise.demo.model.Vote;
import arguewise.demo.types.EntityType;
import arguewise.demo.types.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByUserIdAndEntityIdAndEntityType(Long userId, Long entityId, EntityType entityType);

    long countByEntityTypeAndUser(EntityType entityType, User user);

    @Query("SELECT v.id FROM Vote v WHERE v.entityType = :entityType AND v.user = :user")
    List<Long> findIdForEntityTypeAndUser(@Param("entityType") EntityType entityType, @Param("user") User user);

    @Query("SELECT v.entityId FROM Vote v WHERE v.user.id = :userId AND v.entityId IN :entityIds AND v.entityType = :entityType AND v.voteType = :voteType")
    List<Long> findLikedByUserForEntities(@Param("userId") Long userId, @Param("entityIds") Collection<Long> entityIds, @Param("entityType") EntityType entityType, @Param("voteType") VoteType voteType);

    @Query("SELECT v.entityId, COUNT(v.id) FROM Vote v WHERE v.entityId IN :entityIds AND v.entityType = :entityType AND v.voteType = :voteType GROUP BY v.entityId")
    List<Object[]> findVoteCountsForEntity(@Param("entityIds") Collection<Long> entityIds, @Param("entityType") EntityType entityType, @Param("voteType") VoteType voteType);

    @Query("SELECT v.entityId, v.voteType, COUNT(v.id) FROM Vote v WHERE v.entityId IN :entityIds AND v.entityType = :entityType GROUP BY v.entityId, v.voteType")
    List<Object[]> findVoteCountsForEntity(@Param("entityIds") Collection<Long> entityIds, @Param("entityType") EntityType entityType);


}
