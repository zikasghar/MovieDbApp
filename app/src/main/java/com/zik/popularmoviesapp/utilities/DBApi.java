package com.zik.popularmoviesapp.utilities;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.zik.popularmoviesapp.constants.Constants.APPEND_QUERY;
import static com.zik.popularmoviesapp.constants.Constants.KEY;
import static com.zik.popularmoviesapp.constants.Constants.QUERY_BY_POPULAR;
import static com.zik.popularmoviesapp.constants.Constants.QUERY_BY_RATED;

public interface DBApi {

    @GET(QUERY_BY_POPULAR + KEY)
    Call<JsonObject> getMoviesByPopular(@Query(APPEND_QUERY) String page);

    @GET(QUERY_BY_RATED + KEY)
    Call<JsonObject> getMoviesByRated(@Query(APPEND_QUERY) String page);

}
