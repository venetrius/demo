package arguewise.demo.batch.chatbot.argument;

import arguewise.demo.dto.argument.CreateArgumentDTO;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.UsersDiscussion;

public interface IArgumentCreator {
    public CreateArgumentDTO createArgument(Discussion discussion, UsersDiscussion.Side side);

    }
