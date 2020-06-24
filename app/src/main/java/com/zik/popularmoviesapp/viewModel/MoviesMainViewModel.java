package com.zik.popularmoviesapp.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zik.popularmoviesapp.model.PopularMovie;
import com.zik.popularmoviesapp.utilities.MovieDBRepo;

import java.util.List;

/**
 * Created by Zik Asghar 06/2020
 */

public class MoviesMainViewModel extends ViewModel {
    public MutableLiveData<List<PopularMovie>> movies = new MutableLiveData<>();
    private MovieDBRepo repo = new MovieDBRepo();

    public void init() {
        repo.start();
        movies.postValue(repo.getMovies());
    }

    public LiveData<List<PopularMovie>> getMoviesList() {
        return movies;
    }

}