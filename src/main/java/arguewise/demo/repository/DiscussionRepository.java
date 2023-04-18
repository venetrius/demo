package arguewise.demo.repository;

import arguewise.demo.model.Discussion;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscussionRepository extends JpaRepository< Discussion, Integer> {
    Optional<Discussion> findById(long id);

    boolean existsById(long id);

    void deleteById(long id);

}
