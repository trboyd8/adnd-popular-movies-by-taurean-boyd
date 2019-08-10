package com.taureanboyd.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView movieGrid;
    private MovieListAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.movieGrid = findViewById(R.id.rv_movie_list);
        this.movieGrid.setLayoutManager(new GridLayoutManager(this, 2));

        this.movieAdapter = new MovieListAdapter(this);
        this.movieGrid.setAdapter(movieAdapter);

        getMovies(SortBy.MOST_POPULAR);
    }

    private void getMovies(SortBy sortBy) {
        DownloadAndParseTask task = new DownloadAndParseTask(this, this.movieAdapter);
        task.execute(sortBy);
    }

    public enum SortBy {
        MOST_POPULAR("popular"),
        HIGHEST_RATED("top_rated");

        private String path;

        SortBy(String path) {
            this.path = path;
        }

        String getPath() {
            return this.path;
        }
    }
}
