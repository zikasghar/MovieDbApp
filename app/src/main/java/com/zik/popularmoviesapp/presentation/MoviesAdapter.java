package com.zik.popularmoviesapp.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zik.popularmoviesapp.R;
import com.zik.popularmoviesapp.model.PopularMovie;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> implements Filterable {
    private List<PopularMovie> movies;
    private List<PopularMovie> filteredMovies;
    private final MovieClickHandler clickHandler;

    public interface MovieClickHandler {
        void onClick(PopularMovie m);
    }

    public MoviesAdapter(List<PopularMovie> popularMovies, MovieClickHandler movieClickHandler) {
        clickHandler = movieClickHandler;
        movies = popularMovies;
        filteredMovies = movies;
    }


    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movie_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickHandler.onClick(filteredMovies.get(getAdapterPosition()));
        }
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView v = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item_view, parent, false);
        return new MoviesViewHolder(v);
    }


    @Override
    public void onBindViewHolder(MoviesViewHolder holder, final int position) {
        final PopularMovie movie = filteredMovies.get(position);
        Glide.with(holder.imageView).load(movie.getPosterPath()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return filteredMovies.size();
    }

    public void setAdapterList(List<PopularMovie> movieList) {
        movies = movieList;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredMovies = movies;
                } else {
                    List<PopularMovie> filteredList = new ArrayList<>();
                    for (PopularMovie row : movies) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    filteredMovies = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredMovies;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredMovies = (ArrayList<PopularMovie>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
