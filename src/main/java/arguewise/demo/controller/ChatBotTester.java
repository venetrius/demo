package arguewise.demo.controller;

import arguewise.demo.batch.chatbot.ChatBotRunner;
import arguewise.demo.batch.chatbot.Context;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatbot")
@AllArgsConstructor
public class ChatBotTester {

    @Autowired
    ChatBotRunner chatBotRunner;

    @PostMapping
    public String test() {
        return chatBotRunner.run(Context.createTopicPrompt);
    }
}
