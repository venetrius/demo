package arguewise.demo.repository;

import arguewise.demo.model.Space;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpaceRepository extends JpaRepository<Space, Integer> {
    Optional<Space> findById(long id);

    boolean existsById(long id);

    void deleteById(long id);

}

