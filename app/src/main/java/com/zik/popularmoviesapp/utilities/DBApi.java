package com.zik.popularmoviesapp.utilities;

import com.google.gson.JsonObject;
import com.zik.popularmoviesapp.constants.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DBApi {

        @GET("movie/popular?api_key=" + API.KEY)
        Call<JsonObject> getMoviesByPopular(@Query("page") String page);

        @GET("movie/top_rated?api_key=" + API.KEY)
        Call<JsonObject> getMoviesByRated(@Query("page") String page);

}
