package com.taureanboyd.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents the details of a movie from the Movie DB.
 */
public class Movie {
    private int voteCount;
    private int id;
    private boolean video;
    private double voteAverage;
    private String title;
    private double popularity;
    private String posterPath;
    private String originalLanguage;
    private String originalTitle;
    private int[] genreIds;
    private String backdropPath;
    private boolean isAdult;
    private String overview;
    private Date releaseDate;

    public static Movie fromJSONObject(JSONObject jsonObject) {
        try {
            Movie movie = new Movie();
            movie.setVoteCount(jsonObject.getInt("voteCount"));
            movie.setId(jsonObject.getInt("id"));
            movie.setVideo(jsonObject.getBoolean("video"));
            movie.setVoteAverage(jsonObject.getDouble("vote_average"));
            movie.setTitle(jsonObject.getString("title"));
            movie.setPosterPath(jsonObject.getString("poster_path"));
            movie.setOriginalLanguage(jsonObject.getString("original_language"));
            movie.setOriginalTitle(jsonObject.getString("original_title"));
            movie.setBackdropPath(jsonObject.getString("backdrop_path"));
            movie.setAdult(jsonObject.getBoolean("adult"));
            movie.setOverview(jsonObject.getString("overview"));
            movie.setReleaseDate(new SimpleDateFormat("YYYY-MM-DD").parse(jsonObject.getString("release_date")));

            JSONArray genreIdsJSON = jsonObject.getJSONArray("genre_ids");
            int[] genreIds = new int[genreIdsJSON.length()];
            for (int i = 0; i < genreIdsJSON.length(); i++) {
                genreIds[i] = genreIdsJSON.getInt(i);
            }
            movie.setGenreIds(genreIds);
        }
        catch (JSONException ex) {
            // TODO: What to do here?
        }
        catch (ParseException ex) {
            // TODO: What to do here?
        }
        return null;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(int[] genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
