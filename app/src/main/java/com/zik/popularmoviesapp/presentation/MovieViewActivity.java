package com.zik.popularmoviesapp.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.zik.popularmoviesapp.R;
import com.zik.popularmoviesapp.constants.Constants;
import com.zik.popularmoviesapp.databinding.ActivityMovieViewBinding;
import com.zik.popularmoviesapp.model.PopularMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * Loads view of one movie, showing the poster, title, rating, year released and an overview
 * <p>
 * Created by Zik Asghar 06/2020
 */

public class MovieViewActivity extends AppCompatActivity {
    ActivityMovieViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_view);
        Intent intent = getIntent();
        loadMovie((PopularMovie) intent.getParcelableExtra(Constants.MOVIE));
    }

    /**
     * updates the view to show the details of the movie
     *
     * @param movie the movie that was selected by user
     */

    private void loadMovie(PopularMovie movie) {
        setToolbar(movie);
        try {
            Glide.with(binding.movieView).load(movie.getPosterPath()).into(binding.movieView);
            binding.releaseTv.append(movie.getRelease());
            binding.overviewTv.setText(movie.getOverview());
            setVoteStars(movie);
        } catch (Exception e) {
            finish();
        }
    }

    /**
     * sets the toolbar, with movie title and close option
     *
     * @param movie
     */

    private void setToolbar(PopularMovie movie) {
        binding.toolbar.setTitle(movie.getTitle());
        binding.toolbar.setNavigationIcon(R.drawable.ic_round_close);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * shows the rating of the movie by updating the number of stars
     *
     * @param movie
     */
    private void setVoteStars(PopularMovie movie) {
        // nullability is checked at getting movie details from API,
        // movie would not have loaded into list if any of the details are missing
        List<ImageView> starsArray = new ArrayList<>();
        float stars = movie.getVoteAverage() / 2;
        boolean half_star = false;
        if (movie.getVoteAverage() % 2 > 0) {
            half_star = true;
        }
        binding.rating.setText("(" + stars + ")");
        starsArray.add(binding.star1);
        starsArray.add(binding.star2);
        starsArray.add(binding.star3);
        starsArray.add(binding.star4);
        starsArray.add(binding.star5);
        for (int i = 0; i < (int) stars; i++) {
            starsArray.get(i).setBackgroundResource(R.drawable.star_full);
        }
        if (half_star) {
            starsArray.get((int) stars).setBackgroundResource(R.drawable.star_half);
        }
    }
}
