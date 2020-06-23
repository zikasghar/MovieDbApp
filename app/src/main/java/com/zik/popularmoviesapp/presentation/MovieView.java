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
import com.zik.popularmoviesapp.model.PopularMovie;

import java.util.ArrayList;
import java.util.List;

public class MovieView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_view);
        Intent intent = getIntent();
        loadMovie((PopularMovie) intent.getParcelableExtra("movie"));
    }

    private void loadMovie(PopularMovie movie) {
        setToolbar(movie);
        ImageView movieView = findViewById(R.id.movie_view);
        Glide.with(movieView).load(movie.getPosterPath()).into(movieView);
        TextView date = findViewById(R.id.release_tv);
        date.append(movie.getRelease());
        TextView overview = findViewById(R.id.overview_tv);
        overview.setText(movie.getOverview());
        setVoteStars(movie);
    }

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

    private void setVoteStars(PopularMovie movie) {
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
