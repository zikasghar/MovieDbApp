package com.zik.popularmoviesapp.constants;

import com.zik.popularmoviesapp.BuildConfig;

/**
 * Constant fields used
 * <p>
 * Created by Zik Asghar 06/2020
 */

public class Constants {
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String POSTER_URL = "https://image.tmdb.org/t/p/w185";

    public enum SortBy {POPULAR, RATING}

    public static final String MOVIE = "movie";

    // DPApi
    public static final String KEY = BuildConfig.THE_MOVIE_DATABASE_API_KEY;
    public static final String QUERY_BY_POPULAR = "movie/popular?api_key=";
    public static final String QUERY_BY_RATED = "movie/top_rated?api_key=";
    public static final String APPEND_QUERY = "page";

}
