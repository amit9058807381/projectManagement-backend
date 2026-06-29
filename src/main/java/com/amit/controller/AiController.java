package com.amit.controller;


import com.amit.AIChatDTO.ChatRequest;
import com.amit.AIChatDTO.ChatResponse;
import com.amit.service.GeminiService;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final GeminiService geminiService;

    public AiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/chat")
    public ChatResponse chat(
            @RequestBody ChatRequest request
    ) {

        String answer =
                geminiService.askQuestion(
                        request.getMessage()
                );

        return new ChatResponse(answer);
    }
}