package arguewise.demo.batch.chatbot;

// TODO read from DB
// TODO add swagger documentation to app and use that
public class Context {
    public static final String neutralParticipant = "Imagine you are a user of the website 'ArgueWise'. Your goal is to facilitate productive discussions.";
    public static final String argueWiseSummary =
            "ArgueWise is a platform designed to facilitate structured, respectful, and productive discussions between individuals or groups with different opinions. The main goal is to foster understanding and encourage critical thinking, while helping participants reach a common ground or develop new ideas." +
             "The website has the following entities:\n" +
             "Spaces: can be wide as 'Politics' or narrow as 'The literature of Mark Twain'\n" +
             "Discussion: \n" +
                "--- A discussion is a topic that is being discussed in a space\n" +
                "--- An ideal discussion can be argued 'for' or 'against' " +
             "- Example: Why nuclear plants should not be used anymore\n" +
             "- An argument has a life cycle, a limited time in which users can do different action and see different data\n" +
             "- A user can join an argument either as pro / contra\n" +
             "- A user can post an argument to support their opinion\n" +
             "Argument:\n" +
             "- Part of a discussion, the goal of an argument is to convey information why the pro / contra is the right side\n" +
             "Suggestion:\n" +
             "- A way to refine arguments. Suggest edits to an argument which is on the same side as you\n";

     public static final String createDiscussionPrompt =
             "Please suggest a new discussion in space with id 1.\n" +
             "Please use this JSON format:\n" +
             "{\n" +
             "  \"space_id\": \"PLACEHOLDER_SPACE_ID\",\n" +
             "  \"topic\": \"PLACEHOLDER_TOPIC\",\n" +
             "  \"description\": \"PLACEHOLDER_DESCRIPTION\",\n" +
             "  \"timeLimit\": \"PLACEHOLDER_TIME_LIMIT\", // Format: \"YYYY-MM-DDTHH:MM:SS\"\n" +
             "}\n";

        public static final String createArgumentPrompt = "Please suggest a new unique argument.\n" +
                "Please use this JSON format:\n" +
                "{\n" +
                "  \"title\": \"A short summary\",\n" +
                "  \"side\": \"PLACEHOLDER_SIDE\",\n" +
                "  \"details\": [\"A list of sub-points. Other users might suggest changes having sub points might help to make specific suggestions  \"],\n" +
                "}\n";
}
