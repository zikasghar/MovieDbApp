package com.zik.popularmoviesapp.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.zik.popularmoviesapp.data.MovieDataSource;
import com.zik.popularmoviesapp.data.MoviesDataSourceFactory;
import com.zik.popularmoviesapp.model.PopularMovie;

import static com.zik.popularmoviesapp.constants.Constants.RequestType.POPULAR;
import static com.zik.popularmoviesapp.constants.Constants.RequestType.RATING;
import static com.zik.popularmoviesapp.constants.Constants.RequestType.SEARCH_BY;

/**
 * Created by Zik Asghar 06/2020
 */

public class MoviesMainViewModel extends ViewModel {
    public LiveData<PagedList<PopularMovie>> moviePagedList;
    LiveData<PageKeyedDataSource<Integer, PopularMovie>> liveDataSource;

    public MoviesMainViewModel() {
        getViewByPopular();
    }

    public void getViewByPopular() {
        MoviesDataSourceFactory moviesDataSourceFactory = new MoviesDataSourceFactory(POPULAR, null);
        createPagedList(moviesDataSourceFactory);
    }

    public void getViewByRating() {
        MoviesDataSourceFactory moviesDataSourceFactory = new MoviesDataSourceFactory(RATING, null);
        createPagedList(moviesDataSourceFactory);
    }

    public void getViewBySearch(String searchString) {
        MoviesDataSourceFactory moviesDataSourceFactory = new MoviesDataSourceFactory(SEARCH_BY, searchString);
        createPagedList(moviesDataSourceFactory);
    }

    private void createPagedList(MoviesDataSourceFactory moviesDataSourceFactory) {
        liveDataSource = moviesDataSourceFactory.getLiveDataSource();
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(MovieDataSource.PAGE_SIZE)
                .setPrefetchDistance(5)
                .build();
        LiveData list = new LivePagedListBuilder(moviesDataSourceFactory, pagedListConfig).build();
        moviePagedList = list;
    }

}