package com.zik.popularmoviesapp.presentation;

import android.view.LayoutInflater;
import android.view.MenuItem;
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

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

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
        filteredMovies = movies;
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

    public void sort(final MenuItem item) {
        final Collator collator = Collator.getInstance(Locale.UK);
        if (!filteredMovies.isEmpty()) {
            Collections.sort(filteredMovies, new Comparator<PopularMovie>() {
                @Override
                public int compare(PopularMovie movie1, PopularMovie movie2) {
                    switch (item.getItemId()) {
                        case R.id.order_by_name_A_Z:
                        case R.id.order_by_name_Z_A:
                            return collator.compare(movie1.getTitle(), movie2.getTitle());
                        case R.id.order_by_rating_L_H:
                        case R.id.order_by_rating_H_L:
                            return collator.compare(String.valueOf(movie1.getVoteAverage()),
                                    String.valueOf(movie2.getVoteAverage()));
                        case R.id.order_by_year_O_N:
                        case R.id.order_by_year_N_O:
                            return collator.compare(movie1.getRelease(), movie2.getRelease());
                    }
                    return 0;
                }
            });
            if (item.getItemId() == R.id.order_by_name_Z_A ||
                    item.getItemId() == R.id.order_by_rating_H_L ||
                    item.getItemId() == R.id.order_by_year_N_O) {
                Collections.reverse(filteredMovies);
            }
        }
        notifyDataSetChanged();
    }

/*    public void sortByName(){
        final Collator collator = Collator.getInstance(Locale.UK);
        if (!filteredMovies.isEmpty()) {
            Collections.sort(filteredMovies, new Comparator<PopularMovie>() {
                @Override
                public int compare(PopularMovie movie1, PopularMovie movie2) {
                    return collator.compare(movie1.getTitle(), movie2.getTitle());
                }
            });
        }
        notifyDataSetChanged();
    }

    public void sortByRating(){
        final Collator collator = Collator.getInstance(Locale.UK);
        if (!filteredMovies.isEmpty()) {
            Collections.sort(filteredMovies, new Comparator<PopularMovie>() {
                @Override
                public int compare(PopularMovie movie1, PopularMovie movie2) {
                    return collator.compare(String.valueOf(movie1.getVoteAverage()), String.valueOf(movie2.getVoteAverage()));
                }
            });
        }
        notifyDataSetChanged();

    }

    public void sortByYear(){
        final Collator collator = Collator.getInstance(Locale.UK);
        if (!filteredMovies.isEmpty()) {
            Collections.sort(filteredMovies, new Comparator<PopularMovie>() {
                @Override
                public int compare(PopularMovie movie1, PopularMovie movie2) {
                    return collator.compare(movie1.getRelease(), movie2.getRelease());
                }
            });
        }
        notifyDataSetChanged();
    }

 */

}
