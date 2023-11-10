package arguewise.demo.batch.chatbot.argument;

import arguewise.demo.dto.argument.CreateArgumentDTO;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.UsersDiscussion;

import java.util.Optional;

public interface IArgumentCreator {
    public CreateArgumentDTO createDiscussion(Discussion discussion, UsersDiscussion.Side side);

    }
