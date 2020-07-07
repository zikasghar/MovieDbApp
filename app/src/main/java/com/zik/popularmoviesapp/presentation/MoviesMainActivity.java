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
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zik.popularmoviesapp.R;
import com.zik.popularmoviesapp.constants.Constants;
import com.zik.popularmoviesapp.data.HelperMethods;
import com.zik.popularmoviesapp.databinding.ActivityMainViewBinding;
import com.zik.popularmoviesapp.model.PopularMovie;
import com.zik.popularmoviesapp.model.Trailer;
import com.zik.popularmoviesapp.viewModel.MoviesMainViewModel;

import java.util.List;

import static com.zik.popularmoviesapp.constants.Constants.BLANK;
import static com.zik.popularmoviesapp.constants.Constants.TRAILER;

/**
 * Created by Zik Asghar 06/2020
 */

public class MoviesMainActivity extends AppCompatActivity
        implements MoviesAdapter.MovieClickHandler, SearchView.OnQueryTextListener {
    ActivityMainViewBinding binding;
    RecyclerView.LayoutManager layoutManager;
    MoviesMainViewModel viewModel;
    MoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_view);
        binding.pbLoadingIndicator.setVisibility(View.VISIBLE);
        setAdapter();
        viewModel = new ViewModelProvider(this).get(MoviesMainViewModel.class);
        setObservers();
        setToolbar();
    }

    private void setAdapter() {
        layoutManager = new GridLayoutManager(this,
                HelperMethods.calculateNoOfColumns(this));
        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MoviesAdapter(this, this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * sets observer on the list to ensure when it is updated, adapter is also update
     * (adapter.setAdapterList is called to update adapter once the list is returned from API call
     */
    private void setObservers() {
        viewModel.moviePagedList.observe(this, new Observer<PagedList<PopularMovie>>() {
            @Override
            public void onChanged(PagedList<PopularMovie> popularMovies) {
                adapter.submitList(popularMovies);
            }
        });
        binding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
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
                switch (item.getItemId()) {
                    case R.id.order_by_popularity:
                        viewModel.getViewByPopular();
                        break;
                    case R.id.order_by_rated:
                        viewModel.getViewByRating();
                }
                setObservers();
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
    public void onClick(final PopularMovie m) {
        final Intent intent = new Intent(getApplicationContext(), MovieViewActivity.class);
        binding.pbLoadingIndicator.setVisibility(View.VISIBLE);
        viewModel.getTrailerList(m.getId());
        viewModel.trailerList.observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                intent.putExtra(TRAILER, viewModel.trailerList.getValue().get(0).getKey());
                intent.putExtra(Constants.MOVIE, m);
                startActivity(intent);
            }
        });
        binding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
    }

    /**
     * @param query the search string entered
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        viewModel.getViewBySearch(query);
        viewModel.moviePagedList.observe(this, new Observer<PagedList<PopularMovie>>() {
            @Override
            public void onChanged(PagedList<PopularMovie> popularMovies) {
                adapter.submitList(popularMovies);
            }
        });
        return false;
    }


    @Override
    public boolean onQueryTextChange(String query) {
        if (!query.equals(BLANK)) {
            viewModel.getViewBySearch(query);
        } else {
            viewModel.getViewByPopular();
        }
        setObservers();
        return false;
    }
}


