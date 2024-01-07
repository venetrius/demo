package arguewise.demo.repository;

import arguewise.demo.model.Discussion;
import arguewise.demo.model.User;
import arguewise.demo.model.UsersDiscussion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersDiscussionRepository extends JpaRepository<UsersDiscussion, Long> {
   Optional<UsersDiscussion> findByUserAndDiscussion(User currentUser, Discussion discussion);

   List<UsersDiscussion> findByUserAndDiscussionIdIn(User user, List<Long> discussionIds);

   List<UsersDiscussion> findByUserId(Long userId);

   Page<UsersDiscussion> findByUserId(Long userId, Pageable pageable);

   List<UsersDiscussion> findByDiscussionId(Long discussionId);

   Optional<UsersDiscussion> findByUserIdAndDiscussionId(int id, Long id1);

   @Query("SELECT ud FROM UsersDiscussion ud JOIN ud.discussion d WHERE ud.user.id = :userId AND d.status = 'ACTIVE'")
   List<UsersDiscussion> findActiveByUserId(@Param("userId") long userId);
}
