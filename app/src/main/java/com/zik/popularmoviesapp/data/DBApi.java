package com.zik.popularmoviesapp.data;

import com.zik.popularmoviesapp.model.TMDBMovieResponse;
import com.zik.popularmoviesapp.model.TMDBTrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.zik.popularmoviesapp.constants.Constants.APPEND_QUERY_PAGE;
import static com.zik.popularmoviesapp.constants.Constants.APPEND_QUERY_SEARCH_STRING;
import static com.zik.popularmoviesapp.constants.Constants.QUERY_BY_POPULAR;
import static com.zik.popularmoviesapp.constants.Constants.QUERY_BY_RATED;
import static com.zik.popularmoviesapp.constants.Constants.QUERY_BY_SEARCH_STRING;
import static com.zik.popularmoviesapp.constants.Constants.QUERY_FOR_TRAILERS;
import static com.zik.popularmoviesapp.constants.Constants.THE_MOVIE_DATABASE_API_KEY;

public interface DBApi {

    @GET(QUERY_BY_POPULAR + THE_MOVIE_DATABASE_API_KEY)
    Call<TMDBMovieResponse> getMoviesByPopular(@Query(APPEND_QUERY_PAGE) int page);

    @GET(QUERY_BY_RATED + THE_MOVIE_DATABASE_API_KEY)
    Call<TMDBMovieResponse> getMoviesByRated(@Query(APPEND_QUERY_PAGE) int page);

    @GET(QUERY_BY_SEARCH_STRING + THE_MOVIE_DATABASE_API_KEY)
    Call<TMDBMovieResponse> getMovieBySearchString(@Query(APPEND_QUERY_SEARCH_STRING) String search,
                                                   @Query(APPEND_QUERY_PAGE) int page);

    @GET(QUERY_FOR_TRAILERS + THE_MOVIE_DATABASE_API_KEY)
    Call<TMDBTrailerResponse> getTrailers(@Path("movie_id") String movieId);
}
