package arguewise.demo.batch.chatbot;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ChatBotRunner {


    @Value("${openai.api-key}")
    private String apiKey;
//    @Scheduled(cron = "0 */5 * * * *")
    public String run(String prompt) {
        ArrayList<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("user",prompt));
        System.out.println("Running ChatBot");
        OpenAiService service = new OpenAiService(apiKey);
        ChatCompletionRequest completionRequest = new ChatCompletionRequest();
        completionRequest.setModel("gpt-3.5-turbo-1106");
        completionRequest.setMaxTokens(1000);
        completionRequest.setTemperature(0.9);
        completionRequest.setMessages(messages);

        ChatCompletionResult result = service.createChatCompletion(completionRequest);
        System.out.println("Used resources: " +  result.getUsage().getTotalTokens());
        result.getChoices().forEach(System.out::println);
        System.out.println("Running ChatBot");

        return result.getChoices().get(0).getMessage().getContent();
    }


    public void createNewSpace() {

        System.out.println("Running ChatBot");
    }
}
