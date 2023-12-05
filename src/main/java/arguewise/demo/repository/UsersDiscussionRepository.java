package arguewise.demo.repository;

import arguewise.demo.model.Discussion;
import arguewise.demo.model.User;
import arguewise.demo.model.UsersDiscussion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersDiscussionRepository extends JpaRepository<UsersDiscussion, Long> {
   Optional<UsersDiscussion> findByUserAndDiscussion(User currentUser, Discussion discussion);

   List<UsersDiscussion> findByUserAndDiscussionIdIn(User user, List<Long> discussionIds);
}
