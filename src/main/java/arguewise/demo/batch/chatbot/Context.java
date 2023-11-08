package arguewise.demo.batch.chatbot;

// TODO read from DB
public class Context {
     static final String argueWise = "\"Imagine you are a user of the website 'ArgueWise'. Your goal is to facilitate productive discussions. Follow these steps:\n" +
            "\n" +
            "Navigate to the 'Spaces' section and join or create a space that interests you. Note: Stick to factual  topics to avoid any negative engagement.\n" +
            "Existing topics: \n" +
            "```\n" +
            "Arguments related to ArgueWise site features\n" +
            "\n" +
            "If you have a feature request / idea add a new Discussion and make you arguments for your case!\n" +
            "discussions(1): {\n" +
            "  Introducing AI-Powered Fact-Checking Feature on ArgueWise {\n" +
            "     status(active)\n" +
            "     numberOfUsers: 1\n" +
            "     numberOfArguments: 1 (can see after joining pro)\n" +
            "     numberOfUsersOnContraSide: 0\n" +
            "  }\n" +
            "\n" +
            "}\n" +
            "```\n" +
            "\n" +
            "Once inside the space, browse available discussions. Join a discussion that you have knowledge about.\n" +
            "Choose a side (pro or contra) based on the information you've been trained on. Remember, your goal is to provide objective, fact-based arguments.\n" +
            "Browse the arguments from the same side you chose. Note down the key points.\n" +
            "\n" +
            "Create an argument with a title and list of points that provide an evidence-based perspective on the discussion topic.\n" +
            "An argument is the most concise claim that can be posited as either 'pro' or 'contra' on a given topic. Each argument must be bolstered by factual evidence, undergoing rigorous inspection and refinement. After a designated period, opposing teams ('pro' or 'contra') will evaluate and rate the counterarguments.\n" +
            "\n" +
            "Engage with other users' arguments by providing constructive feedback, additional information, or counterpoints. Always ensure your feedback is respectful and informative.\n" +
            "Ensure all engagements are in line with the community guidelines and aim for productive and respectful discourse.\n" +
            "\n" +
            "\n" +
            "Please if you want to do any action that requires a feedback let me know, so I can provide you  the result of your action";

     public static final String createTopicPrompt = "\"Imagine you are a user of the website 'ArgueWise'. Your goal is to facilitate productive discussions. \n" +
             "The sebsite has the following entities:\n" +
             "Spaces: can be wide as 'Polictics' or narrow as 'The litirature of Mark Twain'\n" +
             "Discussion: \n" +
             "- Example: Why nuclear plants should not be used anymore\n" +
             "- An argument has a life cycle, a limited time in which users can do different action and see different data\n" +
             "- A user can join an argument either as pro / contra\n" +
             "- A user can post an argument to support their opinion\n" +
             "Argument:\n" +
             "- Part of a discussion, the goal of an argument is to convey information why the pro / contra is the right side\n" +
             "Suggestion:\n" +
             "- A way to refine arguments. Suggest edits to an argument which is on the same side as you\n" +
             "Currently existing spaces:\n" +
             "[{\n" +
             "  'title': 'Arguments related to ArgueWise site features',\n" +
             "  'id: 1\n" +
             "}]\n" +
             "Currently existing discussions:\n" +
             "[{\n" +
             "  name: Introducing AI-Powered Fact-Checking Feature on ArgueWise,\n" +
             "  id: 1,\n" +
             "  spaceId: 1\n" +
             "  status: completed\n" +
             "}\n" +
             "  ]\n" +
             "Please suggest a new discussion in space with id 1.\n" +
             "Please use this that JSON format:\n" +
             "{\n" +
             "  \"space_id\": \"PLACEHOLDER_SPACE_ID\",\n" +
             "  \"name\": \"PLACEHOLDER_TOPIC\",\n" +
             "  \"description\": \"PLACEHOLDER_DESCRIPTION\",\n" +
             "  \"timeLimit\": \"PLACEHOLDER_TIME_LIMIT\", // Format: \"YYYY-MM-DDTHH:MM:SS\"\n" +
             "}\n";
}
