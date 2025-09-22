package com.xyz.movie.ai.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GenAIRecommendationService {

    private final String HUGGING_FACE_API_URL = "YOUR_HUGGING_FACE_MODEL_ENDPOINT_URL";
    private final String HUGGING_FACE_API_KEY = "YOUR_HUGGING_FACE_API_KEY"; // Optional, if using a managed service like Inference API

    public String recommendMoviesOrTheatres(List<String> userHistory, String city, String date) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(HUGGING_FACE_API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // The prompt format depends on the specific model used.
        // This is a common format for instruction-following models.
        String prompt = "Based on the user's booking history: " + String.join(", ", userHistory) +
                ", provide 3 movie or theatre recommendations in " + city +
                " for the date " + date + ". Respond in a JSON array of strings.";

        Map<String, Object> body = new HashMap<>();
        body.put("inputs", prompt);
        body.put("parameters", Map.of(
                "return_full_text", false,
                "max_new_tokens", 200
        ));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        // Send the request to your Hugging Face model endpoint
        ResponseEntity<List> response = restTemplate.postForEntity("https://api-inference.huggingface.co/models/gpt2", entity, List.class);

        // The response structure from Hugging Face Inference API is often a list of maps
        if (response.getBody() != null && !response.getBody().isEmpty()) {
            Map<String, String> result = (Map<String, String>) response.getBody().get(0);
            return result.get("generated_text");
        }

        return "{}"; // Return an empty JSON object if there's no result
    }
}