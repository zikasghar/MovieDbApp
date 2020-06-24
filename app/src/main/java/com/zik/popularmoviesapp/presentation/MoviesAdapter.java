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

/**
 * exposes list of movies to a recyclerview
 * extends recyclerview adapter
 * implements view.onClickListener
 * implements Filterable to sort the arrayList
 * <p>
 * <p>
 * Created by Zik Asghar 06/2020
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> implements Filterable {
    private List<PopularMovie> movies;
    private List<PopularMovie> filteredMovies;
    private final MovieClickHandler clickHandler;

    /**
     * interface that receives onClick messages
     */
    public interface MovieClickHandler {
        void onClick(PopularMovie m);
    }

    /**
     *
     * @param popularMovies list of current PopularMovies
     * @param movieClickHandler called when an item is clicked.
     */

    public MoviesAdapter(List<PopularMovie> popularMovies, MovieClickHandler movieClickHandler) {
        clickHandler = movieClickHandler;
        movies = popularMovies;
        filteredMovies = movies;
    }

    /**
     * cache of children views for item
     */

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movie_view);
            itemView.setOnClickListener(this);
        }

        /**
         *
         * @param v the view tht was clicked
         */
        @Override
        public void onClick(View v) {
            clickHandler.onClick(filteredMovies.get(getAdapterPosition()));
        }
    }

    /**
     * created as each item is created
     *
     * @param parent the ViewGroup that these ViewHolders are contained within
     * @param viewType currently an ImageView
     * @return a newMoviesAdapter which holds  viewHolder for each item in the List provided
     */
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView v = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item_view, parent, false);
        return new MoviesViewHolder(v);
    }

    /**
     *
     * @param holder updates to showc contents of the item at given position
     * @param position position within the adapters data set
     */
    @Override
    public void onBindViewHolder(MoviesViewHolder holder, final int position) {
        final PopularMovie movie = filteredMovies.get(position);
        Glide.with(holder.imageView).load(movie.getPosterPath()).into(holder.imageView);
    }

    /**
     * number of items to display
     *
     * @return number of items in the MoviesAdapter
     */
    @Override
    public int getItemCount() {
        return filteredMovies.size();
    }

    /**
     * used to set/update movies on the MoviesAdapter
     *
     * @param movieList list of movies received from the API
     */
    public void setAdapterList(List<PopularMovie> movieList) {
        movies = movieList;
        filteredMovies = movies;
        notifyDataSetChanged();
    }

    /**
     *
     * @return Filter with sorted List
     */
    @Override
    public Filter getFilter() {
        return new Filter() {

            /**
             * @param charSequence
             * @return filtered results
             */
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
}
