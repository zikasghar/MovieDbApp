package com.zik.popularmoviesapp.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.zik.popularmoviesapp.R;
import com.zik.popularmoviesapp.constants.Constants;
import com.zik.popularmoviesapp.model.PopularMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * Loads view of one movie, showing the poster, title, rating, year released and an overview
 * <p>
 * Created by Zik Asghar 06/2020
 */

public class MovieView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_view);
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
        ImageView movieView = findViewById(R.id.movie_view);
        try {
            Glide.with(movieView).load(movie.getPosterPath()).into(movieView);
            TextView date = findViewById(R.id.release_tv);
            date.append(movie.getRelease());
            TextView overview = findViewById(R.id.overview_tv);
            overview.setText(movie.getOverview());
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(movie.getTitle());
        toolbar.setNavigationIcon(R.drawable.ic_round_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
        TextView vote = findViewById(R.id.rating);
        float stars = movie.getVoteAverage() / 2;
        boolean half_star = false;
        if (movie.getVoteAverage() % 2 > 0) {
            half_star = true;
        }
        vote.setText("(" + stars + ")");
        starsArray.add((ImageView) findViewById(R.id.star1));
        starsArray.add((ImageView) findViewById(R.id.star2));
        starsArray.add((ImageView) findViewById(R.id.star3));
        starsArray.add((ImageView) findViewById(R.id.star4));
        starsArray.add((ImageView) findViewById(R.id.star5));
        for (int i = 0; i < (int) stars; i++) {
            starsArray.get(i).setBackgroundResource(R.drawable.star_full);
        }
        if (half_star) {
            starsArray.get((int) stars).setBackgroundResource(R.drawable.star_half);
        }
    }
}
