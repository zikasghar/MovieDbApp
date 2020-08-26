package com.zik.popularmoviesapp.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.zik.popularmoviesapp.R;

import static com.zik.popularmoviesapp.utils.Constants.MOVIE_ID_STRING;
import static com.zik.popularmoviesapp.utils.Constants.TRAILER;
import static com.zik.popularmoviesapp.utils.Constants.YOUTUBE_API_KEY;

public class MoviePlayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    YouTubePlayerView youTubePlayerView;
    String movieId;
    String trailerId;

    @Override
    protected void onCreate(Bundle sis) {
        super.onCreate(sis);
        setContentView(R.layout.movie_player);
        Intent intent = getIntent();
        movieId = intent.getStringExtra(MOVIE_ID_STRING);
        trailerId = intent.getStringExtra(TRAILER);
        youTubePlayerView = findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(YOUTUBE_API_KEY, this);
        setToolbar();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_round_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer youTubePlayer,
                                        boolean b) {
        if (!b) {
            youTubePlayer.cueVideo(trailerId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, 1).show();
        } else {
            String error = R.string.player_error + youTubeInitializationResult.toString();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(YOUTUBE_API_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return null;
    }
}
