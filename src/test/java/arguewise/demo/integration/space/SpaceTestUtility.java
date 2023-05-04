package arguewise.demo.integration.space;

import arguewise.demo.model.Space;
import arguewise.demo.repository.SpaceRepository;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceTestUtility {

    @Autowired
    private SpaceRepository spaceRepository;

    public static Space createSpace(SpaceRepository spaceRepository) {
        Space space = new Space("Technology", "A space for discussing technology-related topics.");
        space = spaceRepository.save(space);
        return space;
    }

    public Space createSpace() {
        Space space = new Space("Technology", "A space for discussing technology-related topics.");
        spaceRepository.save(space);
        return space;
    }

    public Space createSpace(String name, String description) {
        Space space = new Space(name, description);
        spaceRepository.save(space);
        return space;
    }

    public Space findById(Long id) {
        return spaceRepository.findById(id).orElse(null);
    }
}
