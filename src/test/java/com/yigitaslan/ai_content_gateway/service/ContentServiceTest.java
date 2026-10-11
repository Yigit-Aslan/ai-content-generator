package com.yigitaslan.ai_content_gateway.service;

import com.yigitaslan.ai_content_gateway.dto.ContentRequestModel;
import com.yigitaslan.ai_content_gateway.dto.ContentResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class ContentServiceTest {
    private static final String API_URL = "https://example.test/generateContent";

    private MockRestServiceServer server;
    private ContentService contentService;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder().baseUrl(API_URL);
        server = MockRestServiceServer.bindTo(builder).build();
        contentService = new ContentService(builder.build(), new ObjectMapper());
    }

    @Test
    void returnsTranslationAndParsedSafeDecision() {
        expectProviderResponse("""
                {"translatedText":"Merhaba","safe":true,"reason":""}
                """);

        ContentResponseModel response = contentService.processContent(request());

        assertEquals("Merhaba", response.getTranslatedText());
        assertTrue(response.isSafe());
        assertEquals("Processed successfully via Gemini API", response.getModerationMessage());
        server.verify();
    }

    @Test
    void returnsUnsafeDecisionAndReason() {
        expectProviderResponse("""
                {"translatedText":"Translated text","safe":false,"reason":"Contains harmful content"}
                """);

        ContentResponseModel response = contentService.processContent(request());

        assertEquals("Translated text", response.getTranslatedText());
        assertFalse(response.isSafe());
        assertEquals("Contains harmful content", response.getModerationMessage());
        server.verify();
    }

    @Test
    void rejectsMalformedProviderAnalysis() {
        expectProviderResponse("""
                {"translatedText":"Merhaba","reason":""}
                """);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> contentService.processContent(request())
        );

        assertEquals(BAD_GATEWAY, exception.getStatusCode());
        server.verify();
    }

    @Test
    void rejectsNonBooleanSafetyValue() {
        expectProviderResponse("""
                {"translatedText":"Merhaba","safe":"true","reason":""}
                """);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> contentService.processContent(request())
        );

        assertEquals(BAD_GATEWAY, exception.getStatusCode());
        server.verify();
    }

    @Test
    void rejectsMissingProviderCandidate() {
        expectProviderPayload("{\"candidates\":[null]}");

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> contentService.processContent(request())
        );

        assertEquals(BAD_GATEWAY, exception.getStatusCode());
        server.verify();
    }

    private void expectProviderResponse(String analysis) {
        String escapedAnalysis = analysis.strip().replace("\\", "\\\\").replace("\"", "\\\"");
        expectProviderPayload("{\"candidates\":[{\"content\":{\"parts\":[{\"text\":\""
                + escapedAnalysis + "\"}]}}]}");
    }

    private void expectProviderPayload(String payload) {
        server.expect(requestTo(API_URL))
                .andRespond(withSuccess(payload, APPLICATION_JSON));
    }

    private ContentRequestModel request() {
        ContentRequestModel request = new ContentRequestModel();
        request.setText("Hello");
        request.setTargetLanguage("Turkish");
        return request;
    }
}
