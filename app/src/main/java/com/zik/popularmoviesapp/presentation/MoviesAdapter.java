package com.zik.popularmoviesapp.presentation;

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
import com.zik.popularmoviesapp.model.PopularMovie;

import static com.zik.popularmoviesapp.constants.Constants.POSTER_URL;

public class MoviesAdapter extends PagedListAdapter<PopularMovie,
        MoviesAdapter.MovieViewHolder> {
    private static DiffUtil.ItemCallback<PopularMovie> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<PopularMovie>() {
                @Override
                public boolean areItemsTheSame(PopularMovie oldItem, PopularMovie newItem) {
                    return oldItem.getTitle() == newItem.getTitle();
                }

                @Override
                public boolean areContentsTheSame(@NonNull PopularMovie oldItem,
                                                  @NonNull PopularMovie newItem) {
                    return false;
                }
            };
    Context context;

    public interface MovieClickHandler {
        void onClick(PopularMovie m);
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

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        PopularMovie movie = getItem(position);
        if (movie != null) {
            Glide.with(context).load(POSTER_URL + movie.getPosterPath()).into(holder.imageView);
        }
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
