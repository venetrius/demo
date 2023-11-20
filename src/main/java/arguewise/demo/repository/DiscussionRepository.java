package arguewise.demo.repository;

import arguewise.demo.model.Discussion;

import arguewise.demo.model.Space;
import arguewise.demo.types.EntityType;
import arguewise.demo.types.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface DiscussionRepository extends JpaRepository< Discussion, Long> {
    Optional<Discussion> findById(long id);

    boolean existsById(long id);

    void deleteById(long id);

    List<Discussion> findBySpaceIdIn(List<Long> spaceIds);

    List<Discussion> findByStatus(Discussion.DiscussionStatus status);

    @Query("SELECT v.entityId, COUNT(v.id) FROM Vote v WHERE v.entityId IN :argumentIds AND v.entityType = :entityType AND v.voteType = :voteType GROUP BY v.entityId")
    List<Object[]> findVoteCountsForArguments(@Param("argumentIds") Collection<Long> argumentIds, @Param("entityType") EntityType entityType, @Param("voteType") VoteType voteType);

    List<Discussion> findByStatusAndTimeLimitBefore(Discussion.DiscussionStatus status, LocalDateTime timeLimit);

    @Query("SELECT v.entityId FROM Vote v WHERE v.user.id = :userId AND v.entityId IN :argumentIds AND v.entityType = :entityType AND v.voteType = :voteType")
    List<Long> findArgumentsLikedByUser(@Param("userId") Long userId, @Param("argumentIds") Collection<Long> argumentIds, @Param("entityType") EntityType entityType, @Param("voteType") VoteType voteType);

    @Query("SELECT d.topic  FROM Discussion d WHERE space = :space")
    List<String> discussionNamesBySpaceId(@Param("space") Space spaceId);
}
