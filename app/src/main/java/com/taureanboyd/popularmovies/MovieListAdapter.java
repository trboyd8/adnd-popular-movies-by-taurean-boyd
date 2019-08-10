package com.taureanboyd.popularmovies;

import android.content.Context;
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

class MovieListAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private Context context;
    private List<Movie> movies;

    public MovieListAdapter(Context context) {
        this.context = context;
        this.movies = new ArrayList<Movie>();
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

        ImageView moviePoster = movieView.findViewById(R.id.iv_poster);
        Picasso.with(this.context)
                .load(Uri.parse(movie.getPosterPath()))
                .into(moviePoster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
