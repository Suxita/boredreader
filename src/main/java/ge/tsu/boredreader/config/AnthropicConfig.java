package ge.tsu.boredreader.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.anthropic.api.AnthropicApi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AnthropicConfig {

    @Value("${spring.ai.anthropic.api-key}")
    private String apiKey;

    @Value("${spring.ai.anthropic.chat.options.model:claude-3-5-haiku-20241022}")
    private String model;

    @Value("${spring.ai.anthropic.connection.connect-timeout:10000}")
    private Integer connectTimeout;

    @Value("${spring.ai.anthropic.connection.read-timeout:30000}")
    private Integer readTimeout;

    @Value("${spring.ai.anthropic.chat.options.max-tokens:1000}")
    private Integer maxTokens;

    @Bean
    @Primary
    public AnthropicChatModel anthropicChatModel(AnthropicApi anthropicApi) {
        AnthropicChatOptions options = AnthropicChatOptions.builder()
                .model(model) // !
                .maxTokens(maxTokens)  // !
                .build();

        return AnthropicChatModel.builder()
                .anthropicApi(anthropicApi)
                .defaultOptions(options)
                .build();
    }

    @Bean
    public ChatClient chatClient(AnthropicChatModel anthropicChatModel) {
        return ChatClient.builder(anthropicChatModel)
                .build();
    }
}