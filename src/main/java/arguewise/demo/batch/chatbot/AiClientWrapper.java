package arguewise.demo.batch.chatbot;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiClientWrapper implements IAiClientWrapper {
    private final OpenAiService service;

    public AiClientWrapper(@Value("${openai.api-key}") String apiKey) {
        try {
            this.service = new OpenAiService(apiKey);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize OpenAiService", e);
        }
    }

    public OpenAiService getService() {
        return service;
    }

    public ChatCompletionRequest getChatWithDefaultOptions() {
        ChatCompletionRequest completionRequest = new ChatCompletionRequest();
        completionRequest.setModel("gpt-3.5-turbo-1106");
        completionRequest.setMaxTokens(1000);
        completionRequest.setTemperature(0.9);
        return completionRequest;
    }

    public String getResponse(List<ChatMessage> messageList) {
//        String mockResponse = "{\n" +
//                "  \"space_id\": \"1\",\n" +
//                "  \"name\": \"The Impact of Social Media on Society\",\n" +
//                "  \"description\": \"This discussion will explore the positive and negative impacts of social media on individuals and society as a whole. Participants will discuss the influence of social media on mental health, relationships, and societal behaviors.\",\n" +
//                "  \"timeLimit\": \"2023-12-31T23:59:59\"\n" +
//                "}";
//        return mockResponse;
        ChatCompletionRequest completionRequest = getChatWithDefaultOptions();
        completionRequest.setMessages(messageList);
        String result =  service.createChatCompletion(completionRequest).getChoices().get(0).getMessage().getContent();
        System.out.println("Result: " + result);
        return result;
    }
}
