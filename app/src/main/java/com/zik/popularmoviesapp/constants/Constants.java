package com.zik.popularmoviesapp.constants;

import com.zik.popularmoviesapp.BuildConfig;

/**
 * Constant fields used
 * <p>
 * Created by Zik Asghar 06/2020
 */

public class Constants {

    //INTENT EXTRA IDS
    public static final String MOVIE = "movie";
    public static final String MOVIE_ID_STRING = "movie_id";
    // DPApi
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String POSTER_URL = "https://image.tmdb.org/t/p/w185";
    public static final String QUERY_BY_SEARCH_STRING = "search/movie?api_key=";

    public static final String QUERY_BY_POPULAR = "movie/popular?api_key=";
    public static final String QUERY_BY_RATED = "movie/top_rated?api_key=";
    public static final String QUERY_BY_MOVIE_ID = "movie/105/videos?api_key=";
    public static final String THE_MOVIE_DATABASE_API_KEY = BuildConfig.THE_MOVIE_DATABASE_API_KEY;
    public static final String YOUTUBE_API_KEY = BuildConfig.YOUTUBE_API_KEY;

    public enum RequestType {POPULAR, RATING, SEARCH_BY, MOVIE_FROM_ID}

    public static final String APPEND_QUERY_PAGE = "page";
    public static final String APPEND_QUERY_SEARCH_STRING = "query";

}
