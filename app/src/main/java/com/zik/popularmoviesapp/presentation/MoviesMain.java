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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zik.popularmoviesapp.R;
import com.zik.popularmoviesapp.model.PopularMovie;
import com.zik.popularmoviesapp.viewModel.MoviesMainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MoviesMain extends AppCompatActivity
        implements MoviesAdapter.MovieClickHandler, SearchView.OnQueryTextListener {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MoviesAdapter adapter;
    MoviesMainViewModel viewModel;
    List<PopularMovie> moviesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_main_view);
        setAdapters();
        setObservers();
        setToolbar();
    }

    private void setAdapters() {
        adapter = new MoviesAdapter(moviesList, this);
        recyclerView = findViewById(R.id.rv);
        layoutManager = new GridLayoutManager(getApplicationContext(), 3);

    }

    private void setObservers() {
        viewModel = new ViewModelProvider(this).get(MoviesMainViewModel.class);
        viewModel.init();
        viewModel.movies.observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                moviesList = viewModel.getMoviesList().getValue();
                adapter.setAdapterList(moviesList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                findViewById(R.id.pb_loading_indicator).setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setToolbar() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.inflateMenu(R.menu.movie_search_menu);
        SearchView searchView = (SearchView) toolbar.getMenu().findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                adapter.sort(item);
/*                switch (item.getItemId()) {
                    case R.id.order_by_name_A_Z:
                        adapter.sortByName();
                        break;
                    case R.id.order_by_name_Z_A:
                        adapter.sortByName();
                        break;
                    case R.id.order_by_rating_L_H:
                        adapter.sortByRating();
                        break;
                    case R.id.order_by_rating_H_L:
                        adapter.sortByRating();
                        break;
                    case R.id.order_by_year_O_N:
                        adapter.sortByYear();
                        break;
                    case R.id.order_by_year_N_O:
                        adapter.sortByYear();
                        break;
                }
                return true;
*/
                return true;
            }
        });
    }

    @Override
    public void onClick(PopularMovie m) {
        Intent intent = new Intent(getApplicationContext(), MovieView.class);
        intent.putExtra("movie", m);
        startActivity(intent);
    }

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
}

