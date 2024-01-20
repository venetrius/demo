package arguewise.demo.repository;

import arguewise.demo.model.ArgumentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArgumentDetailRepository extends JpaRepository<ArgumentDetail, Long> {

    List<ArgumentDetail> findAllByArgumentId(Long id);
}
