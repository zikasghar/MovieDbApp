package com.zik.popularmoviesapp.data;

import com.zik.popularmoviesapp.model.TMDBApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.zik.popularmoviesapp.constants.Constants.APPEND_QUERY_PAGE;
import static com.zik.popularmoviesapp.constants.Constants.KEY;
import static com.zik.popularmoviesapp.constants.Constants.QUERY_BY_POPULAR;

public interface YoutubeApi {

    @GET(QUERY_BY_POPULAR + KEY)
    Call<TMDBApiResponse> getYoutubeTrailer(@Query(APPEND_QUERY_PAGE) int page);

}
