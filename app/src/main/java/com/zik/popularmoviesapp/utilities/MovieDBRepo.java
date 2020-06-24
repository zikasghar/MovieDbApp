package com.zik.popularmoviesapp.utilities;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zik.popularmoviesapp.constants.Constants;
import com.zik.popularmoviesapp.model.PopularMovie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.zik.popularmoviesapp.constants.Constants.BASE_URL;
import static com.zik.popularmoviesapp.constants.Constants.POSTER_URL;

/**
 * uses Retrofit to get list of movies and details
 * uses Gson to convert to "PopularMovie" object
 * <p>
 * Created by Zik Asghar 06/2020
 */

public class MovieDBRepo {
    private List<PopularMovie> moviesList;

    public void start(Constants.SortBy sortBy) {
        moviesList = new ArrayList<>();
        final int pages = 100;
        final Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        MovieDataBaseApi api = retrofit.create(MovieDataBaseApi.class);
        Call<JsonObject> call;
        for (int i = 1; i < pages; i++) {
            if (sortBy == Constants.SortBy.RATING) {
                call = api.getMoviesByRated(String.valueOf(i));
            } else {
                call = api.getMoviesByPopular(String.valueOf(i));
            }
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call,
                                       @NonNull Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        JsonObject jObj = response.body();
                        assert jObj != null;
                        JsonArray results = jObj.getAsJsonArray("results");
                        for (int i = 0; i < results.size(); i++) {
                            PopularMovie movie = new Gson().fromJson(results.get(i), PopularMovie.class);
                            if (movie.getPosterPath() != null) {
                                movie.setPosterPath(POSTER_URL + movie.getPosterPath());
                                moviesList.add(movie);
                            }
                        }
                    } else {
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                }
            });
        }
    }

    /**
     * @return returns most current list of movies
     */
    public List<PopularMovie> getMovies() {
        return moviesList;
    }

}