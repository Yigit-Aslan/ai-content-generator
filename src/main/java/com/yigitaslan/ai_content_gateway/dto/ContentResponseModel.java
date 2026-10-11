package com.yigitaslan.ai_content_gateway.dto;

public class ContentResponseModel {
    private String originalText;
    private String translatedText;
    private boolean isSafe;
    private String moderationMessage;

    // Constructor
    public ContentResponseModel(String originalText, String translatedText, boolean isSafe, String moderationMessage) {
        this.originalText = originalText;
        this.translatedText = translatedText;
        this.isSafe = isSafe;
        this.moderationMessage = moderationMessage;
    }

    // Getters and Setters
    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public boolean isSafe() {
        return isSafe;
    }

    public void setSafe(boolean safe) {
        isSafe = safe;
    }

    public String getModerationMessage() {
        return moderationMessage;
    }

    public void setModerationMessage(String moderationMessage) {
        this.moderationMessage = moderationMessage;
    }
}
