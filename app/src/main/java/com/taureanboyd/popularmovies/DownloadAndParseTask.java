package com.taureanboyd.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import static android.content.ContentValues.TAG;

public class DownloadAndParseTask extends AsyncTask<MainActivity.SortBy, Long, String> {

    private Context context;
    private MovieListAdapter adapter;

    public DownloadAndParseTask(Context context, MovieListAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    protected String doInBackground(MainActivity.SortBy... sortBy) {

        try {
            URL url = buildUrl(sortBy[0]);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            if (connection.getResponseCode() == 200) {
                Scanner scanner = new Scanner(connection.getInputStream());
                String data = scanner.useDelimiter("\\A").next();
                scanner.close();
                return data;
            } else {
                // TODO: Tell the user we couldn't get the datas.
            }
        } catch (IOException ex) {
            // TODO: We should give the user a heads up... Maybe a Toast?
            Log.w(TAG, "getMovies: We failed to connect to the MovieDB API.", ex);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String movieData) {
        if (movieData != null) {
            this.adapter.setMovies(parseAPIResponse(movieData));
            this.adapter.notifyDataSetChanged();
        }
        super.onPostExecute(movieData);
    }

    private URL buildUrl(MainActivity.SortBy sortBy) {
        String baseUrl = this.context.getString(R.string.movie_db_base_url);
        String apiKeyParam = this.context.getString(R.string.movie_db_api_key_param);
        String apiKey = this.context.getString(R.string.movie_db_api_key);
        String movieListURI = String.format("%s/%s?%s=%s", baseUrl, sortBy.getPath(), apiKeyParam, apiKey);

        try {
            URI uri = URI.create(movieListURI);
            return uri.toURL();
        }
        catch (MalformedURLException ex) {
            Log.d(TAG, "buildUrl: " +
                    movieListURI, ex);

            return null;
        }
    }

    private List<Movie> parseAPIResponse(String response) {
        try {
            JSONObject apiResponse = new JSONObject(response);
            JSONArray results = apiResponse.getJSONArray("results");
            List<Movie> movies = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                Movie movie = Movie.fromJSONObject(results.getJSONObject(i));
                movies.add(movie);
            }
            return movies;
        }
        catch (JSONException ex) {
            return null;
        }
    }
}
