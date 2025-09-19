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
    private final String OPENAI_API_KEY = "YOUR_OPENAI_API_KEY";
    private final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    public String recommendMoviesOrTheatres(List<String> userHistory, String city, String date){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth (OPENAI_API_KEY);
        headers.setContentType (MediaType.APPLICATION_JSON);

        String prompt = "Given the user's booking history: "+ userHistory +
        ", recommend 3 movies or theatres in " + city +
        "for the date" + date + " Respond in JSON with fields: recommendation( array of strings). ";
        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-3.5-turbo");
        body.put("messages", List.of(message));
        body.put("max_tokens", 200);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response  = restTemplate.postForEntity(OPENAI_URL, entity, Map.class);
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response. getBody().get("choices") ;

        String content = (String) ((Map<String, Object>) choices.get(0).get("message")).get("content");

        return content;
    }

}
