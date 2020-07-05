package com.zik.popularmoviesapp.data;

import com.zik.popularmoviesapp.model.TMDBApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.zik.popularmoviesapp.constants.Constants.APPEND_QUERY_PAGE;
import static com.zik.popularmoviesapp.constants.Constants.APPEND_QUERY_SEARCH_STRING;
import static com.zik.popularmoviesapp.constants.Constants.KEY;
import static com.zik.popularmoviesapp.constants.Constants.QUERY_BY_POPULAR;
import static com.zik.popularmoviesapp.constants.Constants.QUERY_BY_RATED;
import static com.zik.popularmoviesapp.constants.Constants.QUERY_BY_SEARCH_STRING;

public interface DBApi {

    @GET(QUERY_BY_POPULAR + KEY)
    Call<TMDBApiResponse> getMoviesByPopular(@Query(APPEND_QUERY_PAGE) int page);

    @GET(QUERY_BY_RATED + KEY)
    Call<TMDBApiResponse> getMoviesByRated(@Query(APPEND_QUERY_PAGE) int page);

    @GET(QUERY_BY_SEARCH_STRING + KEY)
    Call<TMDBApiResponse> getMovieBySearchString(@Query(APPEND_QUERY_SEARCH_STRING) String search,
                                                 @Query(APPEND_QUERY_PAGE) int page);

}
