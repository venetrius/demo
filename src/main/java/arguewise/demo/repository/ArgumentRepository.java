package arguewise.demo.repository;

import arguewise.demo.model.Argument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ArgumentRepository extends JpaRepository<Argument, Long> {

    Collection<Argument> findAllByDiscussionId(Long discussionId);
}
