package com.zik.popularmoviesapp.domain.model;


import java.util.List;

/**
 * GET /movie/{movie_id}/reviews
 */

public class TMDBReviewsResponse {
    public int id;
    public int page;
    public List<Review> results;
    public int total_pages;
    public int total_results;
}
