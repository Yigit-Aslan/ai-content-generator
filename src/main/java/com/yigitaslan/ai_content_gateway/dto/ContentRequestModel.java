package com.yigitaslan.ai_content_gateway.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ContentRequestModel {
    @NotBlank(message = "Metin alanı boş olamaz!")
    @Size(max = 10000, message = "Metin 10000 karakterden uzun olamaz!")
    private String text;

    @NotBlank(message = "Hedef dil alanı boş olamaz!")
    @Size(max = 100, message = "Hedef dil 100 karakterden uzun olamaz!")
    private String targetLanguage;

    // Getters and Setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }
}
