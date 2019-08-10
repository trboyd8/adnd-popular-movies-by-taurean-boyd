package com.taureanboyd.popularmovies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class MovieListAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private List<Movie> movies;

    MovieListAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie, parent, false);
        return new MovieViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        View movieView = movieViewHolder.itemView;
        TextView movieContent = movieView.findViewById(R.id.tv_movie);
        movieContent.setText(this.movies.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
