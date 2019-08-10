package com.taureanboyd.popularmovies;

import android.content.Context;
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

public class MovieDBService {

    private Context context;

    public MovieDBService(Context context) {
        this.context = context;
    }

    public List<Movie> getMovies(SortBy sortBy) {
        // TODO: Check if we have the internet permission
        // TODO: Check if the network is available
        URL movieUrl = buildUrl(sortBy);
        if (movieUrl != null) {
            try {
                HttpsURLConnection connection = (HttpsURLConnection) movieUrl.openConnection();
                if (connection.getResponseCode() == 200) {
                    Scanner scanner = new Scanner(connection.getInputStream());
                    String data = scanner.useDelimiter("\\A").next();
                    scanner.close();
                    return parseAPIResponse(data);
                } else {
                    // TODO: Tell the user we couldn't get the datas.
                }
            } catch (IOException ex) {
                // TODO: We should give the user a heads up... Maybe a Toast?
                Log.w(TAG, "getMovies: We failed to connect to the MovieDB API.", ex);
            }
        }

        // TODO: If we reach here, something went wrong. We should notify the user.
        return null;
    }

    private URL buildUrl(SortBy sortBy) {
        String baseUrl = context.getString(R.string.movie_db_base_url);
        String apiKeyParam = context.getString(R.string.movie_db_api_key_param);
        String apiKey = context.getString(R.string.movie_db_api_key);
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
