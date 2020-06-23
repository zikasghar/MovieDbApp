package com.zik.popularmoviesapp.utilities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDataBaseApi {

    @GET("movie/popular?api_key=39ed02b340235a03c4238222e370adbd")
    Call<Object> getMovies(@Query("page") String page);
}