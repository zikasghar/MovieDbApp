package com.zik.popularmoviesapp.presentation.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zik.popularmoviesapp.R;
import com.zik.popularmoviesapp.domain.model.Movie;

import static com.zik.popularmoviesapp.utils.Constants.POSTER_URL;

/**
 * Uses PagedListAdapter to load content into adapter
 * <p>
 * Created by Zik Asghar 06/2020
 */
public class MoviesAdapter extends PagedListAdapter<Movie,
        MoviesAdapter.MovieViewHolder> {
    private static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Movie>() {
                @Override
                public boolean areItemsTheSame(Movie oldItem, Movie newItem) {
                    return oldItem.getTitle() == newItem.getTitle();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Movie oldItem,
                                                  @NonNull Movie newItem) {
                    return false;
                }
            };
    Context context;

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = getItem(position);
        if (movie != null) {
            Glide.with(context).load(POSTER_URL + movie.getPosterPath()).into(holder.imageView);
        }
    }

    MovieClickHandler clickHandler;

    public MoviesAdapter(Context context, MovieClickHandler clickHandler) {
        super(DIFF_CALLBACK);
        this.clickHandler = clickHandler;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_list_item_view, parent, false);
        return new MovieViewHolder(view);
    }

    public interface MovieClickHandler {
        void onClick(Movie m);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;

        public MovieViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.poster_iv);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickHandler.onClick(getCurrentList().get(getAdapterPosition()));
        }
    }
}
