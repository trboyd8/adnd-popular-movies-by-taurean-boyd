package com.taureanboyd.popularmovies;

import android.app.LauncherActivity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private ListItemClickListener listener;
    private List<Movie> movies;

    public MovieListAdapter(ListItemClickListener listener) {
        this.movies = new ArrayList<>();
        this.listener = listener;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie, parent, false);
        return new MovieViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        Movie movie = this.movies.get(position);
        View movieView = movieViewHolder.itemView;
        movieView.setOnClickListener(movieViewHolder);

        ImageView moviePoster = movieView.findViewById(R.id.iv_poster);
        Picasso.with(movieView.getContext())
                .load(Uri.parse(movie.getPosterPath()))
                .into(moviePoster);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        MovieViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View itemView) {
            listener.onClick(movies.get(getAdapterPosition()));
        }
    }

    interface ListItemClickListener {
        void onClick(Movie movie);
    }
}
