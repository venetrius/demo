package arguewise.demo.repository;

import arguewise.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int id);

    Optional<User> findByEmail(String email);
}
