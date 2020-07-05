package com.zik.popularmoviesapp.data;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.zik.popularmoviesapp.constants.Constants.RequestType;
import com.zik.popularmoviesapp.model.PopularMovie;

public class MoviesDataSourceFactory extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer, PopularMovie>> movieLiveDataSource;
    private RequestType requestType;
    private String searchString;

    public MoviesDataSourceFactory(RequestType requestType, @Nullable String searchString) {
        movieLiveDataSource = new MutableLiveData<>();
        this.requestType = requestType;
        this.searchString = searchString;
    }

    public DataSource<Integer, PopularMovie> create() {
        MovieDataSource movieDataSource = new MovieDataSource(requestType, searchString);
        movieLiveDataSource.postValue(movieDataSource);
        return movieDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, PopularMovie>> getLiveDataSource() {
        return movieLiveDataSource;
    }
}
