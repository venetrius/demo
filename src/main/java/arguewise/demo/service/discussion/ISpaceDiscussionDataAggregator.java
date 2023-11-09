package arguewise.demo.service.discussion;

import arguewise.demo.model.Space;

import java.util.Collection;

public interface ISpaceDiscussionDataAggregator {

    public Collection<String> discussionTitles(Space space);
}
