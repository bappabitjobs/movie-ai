package com.xyz.movie.ai.model;

import lombok.Data;

import java.util.List;

@Data
public class RecommendationRequest {
    private List<String> userHistory;

    private String city;

    private String date;
}
