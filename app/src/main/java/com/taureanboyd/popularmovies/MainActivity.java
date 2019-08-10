package com.taureanboyd.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.movieList = findViewById(R.id.rv_movie_list);
        this.movieList.setLayoutManager(new GridLayoutManager(this, 2));

        MovieDBService movieService = new MovieDBService(this);
        this.movieList.setAdapter(new MovieListAdapter(movieService.getMovies(MovieDBService.SortBy.MOST_POPULAR)));
    }
}
