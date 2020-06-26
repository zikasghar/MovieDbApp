package com.zik.popularmoviesapp.utilities;

import com.google.gson.JsonObject;
import com.zik.popularmoviesapp.BuildConfig;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DBApi {
    String KEY = BuildConfig.THE_MOVIE_DATABASE_API_KEY;
    String QUERY = "movie/popular?api_key=";
    String APPEND_QUERY = "page";

    @GET(QUERY + KEY)
    Call<JsonObject> getMoviesByPopular(@Query(APPEND_QUERY) String page);

    @GET(QUERY + KEY)
    Call<JsonObject> getMoviesByRated(@Query(APPEND_QUERY) String page);

}
