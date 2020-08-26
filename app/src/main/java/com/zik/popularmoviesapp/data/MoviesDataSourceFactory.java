package com.zik.popularmoviesapp.data;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.zik.popularmoviesapp.domain.model.Movie;
import com.zik.popularmoviesapp.utils.Constants.RequestType;

public class MoviesDataSourceFactory extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer, Movie>> movieLiveDataSource;
    private RequestType requestType;
    private String searchString;

    public MoviesDataSourceFactory(RequestType requestType, @Nullable String searchString) {
        movieLiveDataSource = new MutableLiveData<>();
        this.requestType = requestType;
        this.searchString = searchString;
    }

    public DataSource<Integer, Movie> create() {
        MovieDataSource movieDataSource = new MovieDataSource(requestType, searchString);
        movieLiveDataSource.postValue(movieDataSource);
        return movieDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Movie>> getLiveDataSource() {
        return movieLiveDataSource;
    }
}
