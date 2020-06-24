package com.zik.popularmoviesapp.utilities;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Zik Asghar 06/2020
 */

public interface MovieDataBaseApi {

    @GET("movie/popular?api_key=39ed02b340235a03c4238222e370adbd")
    Call<JsonObject> getMovies(@Query("page") String page);
}