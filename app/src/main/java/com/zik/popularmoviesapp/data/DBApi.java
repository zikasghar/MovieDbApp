package com.zik.popularmoviesapp.data;

import com.zik.popularmoviesapp.domain.model.TMDBMovieResponse;
import com.zik.popularmoviesapp.domain.model.TMDBReviewsResponse;
import com.zik.popularmoviesapp.domain.model.TMDBTrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.zik.popularmoviesapp.utils.Constants.APPEND_QUERY_PAGE;
import static com.zik.popularmoviesapp.utils.Constants.APPEND_QUERY_SEARCH_STRING;
import static com.zik.popularmoviesapp.utils.Constants.QUERY_BY_POPULAR;
import static com.zik.popularmoviesapp.utils.Constants.QUERY_BY_RATED;
import static com.zik.popularmoviesapp.utils.Constants.QUERY_BY_SEARCH_STRING;
import static com.zik.popularmoviesapp.utils.Constants.QUERY_FOR_REVIEWS;
import static com.zik.popularmoviesapp.utils.Constants.QUERY_FOR_TRAILERS;
import static com.zik.popularmoviesapp.utils.Constants.THE_MOVIE_DATABASE_API_KEY;

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

    @GET(QUERY_FOR_REVIEWS + THE_MOVIE_DATABASE_API_KEY)
    Call<TMDBReviewsResponse> getReview(@Path("movie_id") String movieId);
}
