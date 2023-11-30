package arguewise.demo.repository;

import arguewise.demo.model.Discussion;
import arguewise.demo.model.User;
import arguewise.demo.model.UsersDiscussion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDiscussionRepository extends JpaRepository<UsersDiscussion, Long> {
    List<UsersDiscussion> findByUserId(Long userId);

    Page<UsersDiscussion> findByUserId(Long userId, Pageable pageable);

    List<UsersDiscussion> findByDiscussionId(Long discussionId);

   Optional<UsersDiscussion> findByUserIdAndDiscussionId(int id, Long id1);

    Optional<UsersDiscussion> findByUserAndDiscussion(User user, Discussion discussion);
}

