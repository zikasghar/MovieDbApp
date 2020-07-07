package com.zik.popularmoviesapp.model;

import java.util.List;

/**
 * Response from Retrofit API call when requesting movies
 * (by popularity, rating or by search string)
 * "page":1,"total_results":10000,"total_pages":500,"results": [{ LIST OF 20 MOVIES PER PAGE}]
 */

public class TMDBMovieResponse {
    public int page;
    public int total_results;
    public int total_pages;
    public List<PopularMovie> results;
}


