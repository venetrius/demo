package arguewise.demo.service;

import arguewise.demo.model.Space;
import arguewise.demo.repository.SpaceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class SpaceService implements ISpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

    @Override
    public List<Space> getAllSpaces() {
        return spaceRepository.findAll();
    }

    @Override
    public Optional<Space> getSpaceById(Long id) {
        return spaceRepository.findById(id);
    }

    @Override
    public Space createSpace(Space space) {
        return spaceRepository.save(space);
    }

    @Override
    public Optional<Space> updateSpace(Long id, Space updatedSpace) {
        return spaceRepository.findById(id).map(space -> {
            space.setName(updatedSpace.getName());
            space.setDescription(updatedSpace.getDescription());
            return spaceRepository.save(space);
        });
    }

    @Transactional
    @Override
    public boolean deleteSpace(Long id) {
        if (spaceRepository.existsById(id)) {
            spaceRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
