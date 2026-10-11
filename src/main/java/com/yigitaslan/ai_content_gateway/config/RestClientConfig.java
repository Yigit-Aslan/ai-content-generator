package com.yigitaslan.ai_content_gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    private final String geminiApiUrl;
    private final String geminiApiKey;

    public RestClientConfig(
            @Value("${gemini.api.url}") String geminiApiUrl,
            @Value("${gemini.api.key}") String geminiApiKey) {
        if (geminiApiKey == null || geminiApiKey.isBlank()) {
            throw new IllegalStateException("GEMINI_API_KEY must be configured");
        }
        this.geminiApiUrl = geminiApiUrl;
        this.geminiApiKey = geminiApiKey;
    }

    @Bean
    public RestClient aiRestClient() {
        return RestClient.builder()
                .baseUrl(geminiApiUrl)
                .defaultHeader("x-goog-api-key", geminiApiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}