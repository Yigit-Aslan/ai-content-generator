package com.yigitaslan.ai_content_gateway.dto.external;

import java.util.List;

public class GeminiRequest {
    private List<Content> contents;
    private GenerationConfig generationConfig;

    public GeminiRequest(List<Content> contents, GenerationConfig generationConfig) {
        this.contents = contents;
        this.generationConfig = generationConfig;
    }

    public List<Content> getContents() { return contents; }
    public GenerationConfig getGenerationConfig() { return generationConfig; }

    public static class GenerationConfig {
        private String responseMimeType;

        public GenerationConfig(String responseMimeType) {
            this.responseMimeType = responseMimeType;
        }

        public String getResponseMimeType() { return responseMimeType; }
    }

    public static class Content {
        private List<Part> parts;

        public Content(List<Part> parts) {
            this.parts = parts;
        }

        public List<Part> getParts() { return parts; }
    }

    public static class Part {
        private String text;

        public Part(String text) {
            this.text = text;
        }

        public String getText() { return text; }
    }
}