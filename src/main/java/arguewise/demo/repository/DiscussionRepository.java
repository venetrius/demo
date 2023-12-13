package arguewise.demo.repository;

import arguewise.demo.model.Discussion;

import arguewise.demo.model.Space;
import arguewise.demo.model.User;
import arguewise.demo.types.EntityType;
import arguewise.demo.types.VoteType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    Page<Discussion> findBySpaceIdIn(List<Long> spaceIds, Pageable pageable);

    List<Discussion> findByStatus(Discussion.DiscussionStatus status);

    @Query("SELECT v.entityId, COUNT(v.id) FROM Vote v WHERE v.entityId IN :argumentIds AND v.entityType = :entityType AND v.voteType = :voteType GROUP BY v.entityId")
    List<Object[]> findVoteCountsForArguments(@Param("argumentIds") Collection<Long> argumentIds, @Param("entityType") EntityType entityType, @Param("voteType") VoteType voteType);

    List<Discussion> findByStatusAndTimeLimitBefore(Discussion.DiscussionStatus status, LocalDateTime timeLimit);

    @Query("SELECT v.entityId FROM Vote v WHERE v.user.id = :userId AND v.entityId IN :argumentIds AND v.entityType = :entityType AND v.voteType = :voteType")
    List<Long> findArgumentsLikedByUser(@Param("userId") Long userId, @Param("argumentIds") Collection<Long> argumentIds, @Param("entityType") EntityType entityType, @Param("voteType") VoteType voteType);

    @Query("SELECT d.topic  FROM Discussion d WHERE space = :space")
    List<String> discussionNamesBySpaceId(@Param("space") Space spaceId);

    long countByStatus(Discussion.DiscussionStatus status);
    Page<Discussion> findByStatus(Discussion.DiscussionStatus status, PageRequest pageable);

//    @Query("Select count(*) from Discussions where status = :status and id in (select discussion.id from UsersDiscussions where user = :user)")
//    long countByUserAndStatus(@Param("user") User user, @Param("status") Discussion.DiscussionStatus discussionStatus);

//    @Query("SELECT COUNT(d) FROM Discussion d WHERE d.status = :status AND d.id IN (SELECT ud.discussion.id FROM UsersDiscussion ud WHERE ud.user = :user)")
//    long countByUserAndStatus(@Param("user") User user, @Param("status") Discussion.DiscussionStatus discussionStatus);

    @Query("SELECT COUNT(d) FROM Discussion d WHERE d.status = :status AND d IN (SELECT ud.discussion FROM UsersDiscussion ud WHERE ud.user = :user)")
    long countByUserAndStatus(@Param("user") User user, @Param("status") Discussion.DiscussionStatus discussionStatus);

//    @Query("Select d from Discussions d where d.status = :status and d.id in (select ud.discussion.id from UsersDiscussions ud where ud.user = :user)")
//    Page<Discussion> findByUserAndStatus(User user, Discussion.DiscussionStatus discussionStatus, Pageable pageable);

//    @Query("Select count(*) from Discussions where status = :status and id in (select discussion.id from UsersDiscussions where user = :user)")
//    Page<Discussion> findByUserAndStatus(User user, Discussion.DiscussionStatus discussionStatus, PageRequest singleItem);

    @Query("SELECT d FROM Discussion d WHERE d.status = :status AND d IN (SELECT ud.discussion FROM UsersDiscussion ud WHERE ud.user = :user)")
    Page<Discussion> findByUserAndStatus(@Param("user") User user, @Param("status") Discussion.DiscussionStatus discussionStatus, Pageable pageable);

}
