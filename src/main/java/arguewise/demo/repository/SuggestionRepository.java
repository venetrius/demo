package arguewise.demo.repository;

import arguewise.demo.model.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
    List<Suggestion> findByArgumentId(Long argumentId);
}
