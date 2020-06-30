package com.zik.popularmoviesapp.presentation;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zik.popularmoviesapp.R;
import com.zik.popularmoviesapp.constants.Constants;
import com.zik.popularmoviesapp.databinding.ActivityMoviesMainViewBinding;
import com.zik.popularmoviesapp.model.PopularMovie;
import com.zik.popularmoviesapp.utilities.HelperFunctions;
import com.zik.popularmoviesapp.viewModel.MoviesMainViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zik Asghar 06/2020
 */

public class MoviesMainActivity extends AppCompatActivity
        implements MoviesAdapter.MovieClickHandler, SearchView.OnQueryTextListener {
    ActivityMoviesMainViewBinding binding;
    RecyclerView.LayoutManager layoutManager;
    MoviesAdapter adapter;
    MoviesMainViewModel viewModel;
    List<PopularMovie> moviesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_main_view);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movies_main_view);
        setAdapters();
        setObservers();
        setToolbar();
    }

    /**
     * sets adapter with list provided, initially empty, later updated from Api
     */
    private void setAdapters() {
        adapter = new MoviesAdapter(moviesList, this);
        layoutManager = new GridLayoutManager(getApplicationContext(),
                HelperFunctions.calculateNoOfColumns(getApplicationContext()));
    }

    /**
     * sets observer on the list to ensure when it is updated, adapter is also update
     * (adapter.setAdapterList is called to update adapter once the list is returned from API call
     */

    private void setObservers() {
        viewModel = new ViewModelProvider(this).get(MoviesMainViewModel.class);
        viewModel.getMovies(Constants.SortBy.POPULAR);
        viewModel.movies.observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                if (moviesList.size() == 0) {
                    binding.pbLoadingIndicator.setVisibility(View.VISIBLE);
                    moviesList = viewModel.getMoviesList(Constants.SortBy.POPULAR).getValue();
                } else {
                    binding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
                    adapter.setAdapterList(moviesList);
                    binding.rv.setLayoutManager(layoutManager);
                    binding.rv.setAdapter(adapter);
                }
            }
        });
    }

    /**
     * sets toolbar
     * - search functionality (using SearchView)
     * - menu options for sorting the adapter
     */

    private void setToolbar() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        binding.toolbar.setTitle(R.string.app_name);
        binding.toolbar.inflateMenu(R.menu.movie_search_menu);
        SearchView searchView = (SearchView) binding.toolbar.getMenu().findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                sort(item);
                return true;
            }
        });
    }

    /**
     * displays selected movie in new Activity
     *
     * @param m the movie that has been clicked on
     */

    @Override
    public void onClick(PopularMovie m) {
        binding.pbLoadingIndicator.setVisibility(View.VISIBLE);
        Intent intent = new Intent(getApplicationContext(), MovieViewActivity.class);
        intent.putExtra(Constants.MOVIE, m);
        startActivity(intent);
        binding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
    }

    /**
     * @param query the search string entered
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        adapter.getFilter().filter(query);
        return false;
    }

    /**
     * sorts the list in numerical order
     *
     * @param item decides which item the list needs to be sorted by
     *             (rating or popularity)
     */

    public void sort(final MenuItem item) {
        if (!moviesList.isEmpty()) {
            switch (item.getItemId()) {
                case R.id.order_by_popularity:
                    moviesList = viewModel.getMoviesList(Constants.SortBy.POPULAR).getValue();
                    break;
                case R.id.order_by_rated:
                    moviesList = viewModel.getMoviesList(Constants.SortBy.RATING).getValue();
            }
        }
    }

}


