package arguewise.demo.repository;

import arguewise.demo.model.Argument;
import arguewise.demo.model.UsersDiscussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ArgumentRepository extends JpaRepository<Argument, Long> {

    Collection<Argument> findAllByDiscussionId(Long discussionId);

    @Query("SELECT a FROM Argument a JOIN UsersDiscussion ud ON a.discussion.id = ud.discussion.id AND a.author.id = ud.user.id WHERE a.discussion.id = :discussionId AND ud.side = :side")
    Collection<Argument> findAllByDiscussionIdAndSide(@Param("discussionId") Long discussionId, @Param("side") UsersDiscussion.Side side);

    @Query("SELECT a.title " +
        "FROM Argument a INNER JOIN UsersDiscussion ud ON a.author.id = ud.user.id " +
        "WHERE ud.side = :side AND a.discussion.id = :discussionId")
    List<String> findTitlesByDiscussionIdAndProSide(@Param("discussionId") Long discussionId, @Param("side")UsersDiscussion.Side side);

    List<Argument> findAllByDiscussionIdInAndSide(List<Long> discussionIds, UsersDiscussion.Side side);
}
