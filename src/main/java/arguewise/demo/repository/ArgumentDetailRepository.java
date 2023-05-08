package arguewise.demo.repository;

import arguewise.demo.model.Argument;
import arguewise.demo.model.ArgumentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ArgumentDetailRepository extends JpaRepository<ArgumentDetail, Long> {

}
