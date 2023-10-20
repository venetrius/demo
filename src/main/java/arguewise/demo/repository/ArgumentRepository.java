package arguewise.demo.repository;

import arguewise.demo.model.Argument;
import arguewise.demo.model.UsersDiscussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface ArgumentRepository extends JpaRepository<Argument, Long> {

    Collection<Argument> findAllByDiscussionId(Long discussionId);

    @Query("SELECT a FROM Argument a JOIN UsersDiscussion ud ON a.discussion.id = ud.discussion.id AND a.author.id = ud.user.id WHERE a.discussion.id = :discussionId AND ud.side = :side")
    Collection<Argument> findAllByDiscussionIdAndSide(@Param("discussionId") Long discussionId, @Param("side") UsersDiscussion.Side side);
}
