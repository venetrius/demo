package arguewise.demo.repository;

import arguewise.demo.model.Space;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpaceRepository extends JpaRepository<Space, Integer> {
    Optional<Space> findById(long id);

    boolean existsById(long id);

    void deleteById(long id);

    Page<Space> findByIdNotIn(List<Long> spacesIdsToExclude, PageRequest pageRequest);

    Page<Space> findAll(Pageable pageable);
}

