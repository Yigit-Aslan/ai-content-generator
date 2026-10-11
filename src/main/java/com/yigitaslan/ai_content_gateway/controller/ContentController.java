package com.yigitaslan.ai_content_gateway.controller;

import com.yigitaslan.ai_content_gateway.dto.ContentRequestModel;
import com.yigitaslan.ai_content_gateway.dto.ContentResponseModel;
import com.yigitaslan.ai_content_gateway.service.ContentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/content")
public class ContentController {
    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @PostMapping("/process")
    public ResponseEntity<ContentResponseModel> processContent(@Valid @RequestBody ContentRequestModel request) {
        ContentResponseModel response = contentService.processContent(request);
        return ResponseEntity.ok(response);
    }
}
