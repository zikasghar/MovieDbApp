package com.zik.popularmoviesapp.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.zik.popularmoviesapp.R;
import com.zik.popularmoviesapp.domain.model.Movie;
import com.zik.popularmoviesapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.zik.popularmoviesapp.utils.Constants.POSTER_URL;
import static com.zik.popularmoviesapp.utils.Constants.TRAILER;

/**
 * Loads view of one movie, showing the poster, title, rating, year released and an overview
 * <p>
 * Created by Zik Asghar 06/2020
 */

public class MovieViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);
        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(Constants.MOVIE);
        setListeners();
        loadMovie(movie);
    }

    private void setListeners() {
        ImageButton trailerBtn = findViewById(R.id.watch_trailer_btn);
        final String trailerKey = getIntent().getStringExtra(TRAILER);
        trailerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trailerIntent = new Intent(getApplicationContext(), MoviePlayer.class);
                trailerIntent.putExtra(TRAILER, trailerKey);
                startActivity(trailerIntent);
            }
        });
        findViewById(R.id.vote_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * updates the view to show the details of the movie
     *
     * @param movie the movie that was selected by user
     */

    private void loadMovie(Movie movie) {
        setToolbar(movie);
        ImageView movieView = findViewById(R.id.poster_iv);
        try {
            Glide.with(movieView).load(POSTER_URL + movie.getPosterPath()).into(movieView);
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

    private void setToolbar(Movie movie) {
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
    private void setVoteStars(Movie movie) {
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