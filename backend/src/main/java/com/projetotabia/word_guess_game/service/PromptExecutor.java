package com.projetotabia.word_guess_game.service;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PromptExecutor {
    private final @NotNull String apiKey;
    private final @NotNull String modelName;

    public PromptExecutor(@Value("demo") @NotNull String apiKey, @Value("gpt-4") @NotNull String modelName) {
        this.apiKey = apiKey;
        this.modelName = modelName;
    }

    private @NotNull OpenAiChatModel createModel() {
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .build();
    }

    public @NotNull String execute(@NotNull String prompt, @Nullable String message) {
        var model = createModel();

        ChatMemory memory = MessageWindowChatMemory.withMaxMessages(10);
        memory.add(new SystemMessage(prompt));

        if (message != null) {
            memory.add(new UserMessage(message));
        }

        Response<AiMessage> response = model.generate(memory.messages());
        return response.content().text();
    }
}
