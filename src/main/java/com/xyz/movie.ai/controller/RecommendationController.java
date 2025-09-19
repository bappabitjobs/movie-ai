package com.xyz.movie.ai.controller;

import com.xyz.movie.ai.model.RecommendationRequest;
import com.xyz.movie.ai.service.GenAIRecommendationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommend")
public class RecommendationController {
    private GenAIRecommendationService genAIRecommendationService;
    public RecommendationController (GenAIRecommendationService genAIRecommendationService){
       this.genAIRecommendationService = genAIRecommendationService;
    }

    @PostMapping("/movies-or-theatres")
    public String recommend (@RequestBody RecommendationRequest request) {
        return genAIRecommendationService.recommendMoviesOrTheatres( request.getUserHistory(), request.getCity(), request.getDate());

    }
}
