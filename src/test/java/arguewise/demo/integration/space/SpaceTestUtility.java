package arguewise.demo.integration.space;

import arguewise.demo.model.Space;
import arguewise.demo.repository.SpaceRepository;

public class SpaceTestUtility {
    public static Space createSpace(SpaceRepository spaceRepository) {
        Space space = new Space(null, "Technology", "A space for discussing technology-related topics.");
        space = spaceRepository.save(space);
        return space;
    }
}
