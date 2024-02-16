package arguewise.demo.batch.chatbot.space;

import arguewise.demo.batch.chatbot.Context;
import arguewise.demo.batch.chatbot.IAiClientWrapper;
import arguewise.demo.model.Discussion;
import arguewise.demo.model.Space;
import arguewise.demo.model.User;
import arguewise.demo.model.UsersDiscussion;
import arguewise.demo.service.ISpaceService;
import arguewise.demo.service.IUserSpaceService;
import arguewise.demo.service.VoteService;
import arguewise.demo.service.util.IJsonParser;
import arguewise.demo.types.EntityType;
import arguewise.demo.types.VoteType;
import com.google.gson.JsonObject;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SpaceVoter {
    private final ISpaceService spaceService;

    private final VoteService voteService;

    private final IAiClientWrapper aiServiceProvider;

    private final IJsonParser parser;

    private final IUserSpaceService userSpaceService;


    // TODO reconsider return type
    public String voteSpace(User user) {
        System.out.println("User: " + user.getId());
        List<Space> spaces = loadSpacesToChooseFrom(user);
        if(spaces == null || spaces.isEmpty()) {
            System.out.println("returning from voteSpace with nothing");
            return "";
        }
        System.out.println("loaded spaces: " +
                spaces
                .stream()
                .map(Space::getId)
                .map(Object::toString)
                .collect(Collectors.joining()));

        List<ChatMessage> messages = createMessageList(spaces);
        String responseString = aiServiceProvider.getResponse(messages);
        JsonObject rootObj = parser.parse(responseString);
        System.out.println(responseString);

        long spaceId = Long.parseLong(
                rootObj.get("spaceId")
                        .toString()
                        .replace("\"", "")
        );
        System.out.println("spaceId:" + spaceId);

        voteService.castVoteInternal(spaceId, EntityType.SPACE, VoteType.UPVOTE, user);
        System.out.println("saved");
        return "User " + user.getId() + " joined space with id: " + spaceId;
    }

    private List<Space> loadSpacesToChooseFrom(User user) {
        List<Space> spaces = new ArrayList<>();
        long totalNumberOfSpaces = spaceService.getSpaceCount();
        long numberOfLikedSpaces = voteService.getNumberOfVoteForEntityTypeAndUser(EntityType.SPACE, user);
        System.out.println("totalNumberOfSpaces: " + totalNumberOfSpaces + ", numberOfLikedSpaces: " + numberOfLikedSpaces);

        if(totalNumberOfSpaces == numberOfLikedSpaces || numberOfLikedSpaces > 5) return null;
        if(numberOfLikedSpaces == 0 || numberOfLikedSpaces  * 100 / totalNumberOfSpaces < 80) {

            List<Long> likedSpaces = voteService.findIdForForEntityTypeAndUser(EntityType.SPACE, user);

            PageRequest pageRequest = PageRequest.of(0, 10);
            if(likedSpaces.isEmpty()){
                spaces = spaceService.getAllSpaces(pageRequest).get().toList();
            } else {
                spaces = spaceService.getSpacesWhereIdNotIn(pageRequest, likedSpaces).get().toList();
            }
            System.out.println(
                    likedSpaces
            );
            if(spaces.isEmpty()) return null;
            System.out.println("size:" + spaces.size());
            return spaces;
        }
        return spaces;
    }

    private String formatSpaceInfo(Space space){
        return "Name: " + space.getName() + "\n"
                + "ID: " + space.getId() + "\n"
                + "Description: " + space.getDescription();
    }

    private List<ChatMessage> createMessageList(List<Space> spaces) {
        ArrayList<ChatMessage> messages = new ArrayList<>();

        String introMessage = Context.neutralParticipant + Context.argueWiseSummary;
        messages.add(new ChatMessage("user",introMessage));

        List<String> spaceInfo = spaces.stream().map(this::formatSpaceInfo).toList();
        messages.add(new ChatMessage(
                "user",
                "existing discussions: \n" + String.join("\n ___________ \n", spaceInfo)
        ));

        messages.add(new ChatMessage("user", Context.likeSpacePrompt));
        return messages;
    }


}
