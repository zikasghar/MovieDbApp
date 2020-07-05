package com.zik.popularmoviesapp.model;

import java.util.List;

public class TMDBApiResponse {
        public int page;
        public int total_results;
        public int total_pages;
        public List<PopularMovie> results;
}

//"page":1,"total_results":10000,"total_pages":500,"results"
