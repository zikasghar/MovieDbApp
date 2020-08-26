package com.zik.popularmoviesapp.domain.model;

import java.util.List;

/**
 * Response from Retrofit API call when requesting movie trailers
 * GET /movie/{movie_id}/videos
 * "id":105,"results":[{ LIST OF AVAILABLE TRAILER }]
 */
public class TMDBTrailerResponse {
    public int id;
    public List<Trailer> results;
}
