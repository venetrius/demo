package arguewise.demo.repository;

import arguewise.demo.model.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
    List<Suggestion> findByArgumentId(Long argumentId);

    @Query("SELECT s.id FROM Suggestion s WHERE s.status = :status")
    Collection<Long> findIdsByStatus(Suggestion.SuggestionStatus status);
}
