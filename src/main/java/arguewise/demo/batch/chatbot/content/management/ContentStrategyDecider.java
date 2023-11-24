package arguewise.demo.batch.chatbot.content.management;

import arguewise.demo.model.User;
import arguewise.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class ContentStrategyDecider {

    private final UserRepository userRepository;

    public Actions chooseAction() {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 2);
        return Actions.values()[randomNum];
    }

    public User chooseUser() {
        List<User> users = userRepository.findByIsBot(true);
        if(users.isEmpty()) {
            throw new RuntimeException("No bot users found");
        }
        return users.get(ThreadLocalRandom.current().nextInt(0, users.size()));
    }
}
