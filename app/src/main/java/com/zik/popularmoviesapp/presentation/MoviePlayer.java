package com.zik.popularmoviesapp.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.zik.popularmoviesapp.R;

import static com.zik.popularmoviesapp.constants.Constants.MOVIE_ID_STRING;
import static com.zik.popularmoviesapp.constants.Constants.YOUTUBE_API_KEY;

public class MoviePlayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle sis) {
        super.onCreate(sis);
        setContentView(R.layout.movie_player);
        Intent intent = getIntent();
        String id = intent.getStringExtra(MOVIE_ID_STRING);
        youTubePlayerView = findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(YOUTUBE_API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer youTubePlayer,
                                        boolean b) {
        final String trailerKey = getIntent().getStringExtra("trailerKey");
        if (!b) {
            youTubePlayer.cueVideo("fhWaJi1Hsfo"); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, 1).show();
        } else {
            String error = R.string.player_error + youTubeInitializationResult.toString();
            Log.d("YOUTUBE ERROR!!!!", error);
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
