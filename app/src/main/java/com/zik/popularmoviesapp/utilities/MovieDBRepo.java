package com.zik.popularmoviesapp.utilities;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zik.popularmoviesapp.model.PopularMovie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.zik.popularmoviesapp.constants.Constants.BASE_URL;
import static com.zik.popularmoviesapp.constants.Constants.JSON_AVERAGE_VOTE_STRING;
import static com.zik.popularmoviesapp.constants.Constants.JSON_OVERVIEW_STRING;
import static com.zik.popularmoviesapp.constants.Constants.JSON_POSTER_PATH_END;
import static com.zik.popularmoviesapp.constants.Constants.JSON_POSTER_PATH_STRING;
import static com.zik.popularmoviesapp.constants.Constants.JSON_RELEASE_DATE_LENGTH;
import static com.zik.popularmoviesapp.constants.Constants.JSON_RELEASE_STRING;
import static com.zik.popularmoviesapp.constants.Constants.JSON_TITLE_STRING;
import static com.zik.popularmoviesapp.constants.Constants.POSTER_URL;

public class MovieDBRepo {
    private List<PopularMovie> moviesList = new ArrayList<>();

    public void start() {
        final int pages = 100;
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        MovieDataBaseApi api = retrofit.create(MovieDataBaseApi.class);
        Call<Object> call;
        for (int i = 1; i < pages; i++) {
            call = api.getMovies(String.valueOf(i));
            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call,
                                       Response<Object> response) {
                    if (response.isSuccessful()) {
                        String jsonString = String.valueOf(response.body());
                        String remainingJsonString = jsonString.substring(jsonString.indexOf("["));
                        for (int i = 0; i < 19; i++) {
                            try {
                                PopularMovie popularMovie = new PopularMovie();
                                String movie = remainingJsonString.
                                        substring(remainingJsonString.indexOf("{"), remainingJsonString.indexOf("}") + 1);
                                remainingJsonString = remainingJsonString.
                                        substring(remainingJsonString.indexOf("{", 5));

                                //GET TITLE FROM JSON STRING
                                int titleIndexStart = movie.indexOf(JSON_TITLE_STRING) + JSON_TITLE_STRING.length();
                                int titleIndexEnd = movie.indexOf(",", titleIndexStart);
                                popularMovie.setTitle(
                                        movie.substring(titleIndexStart, titleIndexEnd));
                                //GET POSTERPATH FROM JSON STRING
                                int posterPathIndexStart = movie.indexOf(JSON_POSTER_PATH_STRING) + JSON_POSTER_PATH_STRING.length();
                                int posterPathIndexEnd = movie.indexOf(JSON_POSTER_PATH_END, posterPathIndexStart);
                                popularMovie.setPosterPath(POSTER_URL +
                                        movie.substring(posterPathIndexStart, posterPathIndexEnd + 4));
                                //GET VOTE AVERAGE FROM JSON STRING
                                int voteIndexStart = movie.indexOf(JSON_AVERAGE_VOTE_STRING) + JSON_AVERAGE_VOTE_STRING.length();
                                int voteIndexEnd = movie.indexOf(",", voteIndexStart);
                                popularMovie.setVoteAverage(
                                        Float.parseFloat(movie.substring(voteIndexStart, voteIndexEnd)));
                                //GET OVERVIEW FROM JSON STRING
                                int overviewIndexStart = movie.indexOf(JSON_OVERVIEW_STRING) + JSON_OVERVIEW_STRING.length();
                                int overviewIndexEnd = movie.indexOf(".,", overviewIndexStart);
                                popularMovie.setOverview(movie.substring(overviewIndexStart, overviewIndexEnd));

                                //GET RELEASE DATE FROM JSON STRING
                                int releaseIndexStart = movie.indexOf(JSON_RELEASE_STRING) + JSON_RELEASE_STRING.length();
                                popularMovie.setRelease(movie.substring(releaseIndexStart, releaseIndexStart + JSON_RELEASE_DATE_LENGTH));

                                //ADD MOVIE TO THE LIST
                                moviesList.add(popularMovie);
                            } catch (Exception e) {
                                Log.d("!!!!!!!!!!!", remainingJsonString);
                            }
                        }
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Log.d("onFailure", t.getLocalizedMessage());
                }
            });
        }
    }

    public List<PopularMovie> getMovies() {
        return moviesList;
    }

}