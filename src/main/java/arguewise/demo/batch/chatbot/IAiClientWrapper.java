package arguewise.demo.batch.chatbot;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import java.util.List;

public interface IAiClientWrapper {

    public OpenAiService getService();

    public ChatCompletionRequest getChatWithDefaultOptions();

    public String getResponse(List<ChatMessage> messageList);

}
