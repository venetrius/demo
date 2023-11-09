package arguewise.demo.service.discussion;

import arguewise.demo.model.Discussion;
import arguewise.demo.model.Space;
import arguewise.demo.repository.DiscussionRepository;
import arguewise.demo.repository.SpaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SpaceDiscussionDataAggregator implements ISpaceDiscussionDataAggregator {

    @Autowired
    private DiscussionRepository discussionRepository;

    public Collection<String> discussionTitles(Space space) {
        return discussionRepository.discussionNamesBySpaceId(space);
//        Collection<Discussion> discussions = discussionRepository.discussionNamesBySpaceId(space.getId());
//        ArrayList<String> titles = new ArrayList<>();
//        return discussionRepository.discussionNamesBySpaceId(space.getId())
//                .stream()
//                .map(Discussion::getTopic)
//                .collect(Collectors.toList());
    }
}
