package com.zik.popularmoviesapp.utilities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDataBaseApi {

    @GET("movie/popular?api_key=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    Call<Object> getMovies(@Query("page") String page);
}
