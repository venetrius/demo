package arguewise.demo.repository;

import arguewise.demo.model.Space;
import arguewise.demo.model.User;
import arguewise.demo.model.UserSpace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSpaceRepository extends JpaRepository<UserSpace, Long> {
    Optional<UserSpace> findByUserAndSpace(User user, Space space);

    List<UserSpace> findByUserId(long id);
}
