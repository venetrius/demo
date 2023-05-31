package arguewise.demo.repository;

import arguewise.demo.model.Discussion;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiscussionRepository extends JpaRepository< Discussion, Long> {
    Optional<Discussion> findById(long id);

    boolean existsById(long id);

    void deleteById(long id);


    List<Discussion> findBySpaceIdIn(List<Long> spaceIds);

}
