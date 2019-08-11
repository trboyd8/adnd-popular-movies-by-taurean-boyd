package com.taureanboyd.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * This class represents the details of a movie from the Movie DB.
 */
public class Movie implements Parcelable {

    private static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";
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

    public Movie() {
    }

    public Movie(Parcel movieAsParcel) {
        if (movieAsParcel != null) {
            this.voteCount = movieAsParcel.readInt();
            this.id = movieAsParcel.readInt();
            this.video = movieAsParcel.readInt() == 1;
            this.voteAverage = movieAsParcel.readDouble();
            this.title = movieAsParcel.readString();
            this.popularity = movieAsParcel.readDouble();
            this.posterPath = movieAsParcel.readString();
            this.originalLanguage = movieAsParcel.readString();
            this.originalTitle = movieAsParcel.readString();
            movieAsParcel.readIntArray(genreIds);
            this.backdropPath = movieAsParcel.readString();
            this.isAdult = movieAsParcel.readInt() == 1;
            this.overview = movieAsParcel.readString();
            try {
                this.releaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(movieAsParcel.readString());
            }
            catch (ParseException ex) {
                Log.w(TAG, "Movie: Failed to parse date.", ex);
            }
        }
    }

    public static Movie fromJSONObject(JSONObject jsonObject) {
        try {
            Movie movie = new Movie();
            movie.setVoteCount(jsonObject.getInt("vote_count"));
            movie.setId(jsonObject.getInt("id"));
            movie.setVideo(jsonObject.getBoolean("video"));
            movie.setVoteAverage(jsonObject.getDouble("vote_average"));
            movie.setTitle(jsonObject.getString("title"));
            movie.setPosterPath(String.format("%s%s", POSTER_BASE_URL, jsonObject.getString("poster_path")));
            movie.setOriginalLanguage(jsonObject.getString("original_language"));
            movie.setOriginalTitle(jsonObject.getString("original_title"));
            movie.setBackdropPath(jsonObject.getString("backdrop_path"));
            movie.setAdult(jsonObject.getBoolean("adult"));
            movie.setOverview(jsonObject.getString("overview"));
            movie.setReleaseDate(new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("release_date")));

            JSONArray genreIdsJSON = jsonObject.getJSONArray("genre_ids");
            int[] genreIds = new int[genreIdsJSON.length()];
            for (int i = 0; i < genreIdsJSON.length(); i++) {
                genreIds[i] = genreIdsJSON.getInt(i);
            }
            movie.setGenreIds(genreIds);

            return movie;
        }
        catch (JSONException ex) {
            Log.w(TAG, "fromJSONObject: Failed to parse the movie details", ex);
            // TODO: What to do here?
        }
        catch (ParseException ex) {
            Log.w(TAG, "fromJSONObject: Failed to parse the movie details", ex);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.voteCount);
        parcel.writeInt(this.id);
        parcel.writeInt(this.video ? 1 : 0);
        parcel.writeDouble(this.voteAverage);
        parcel.writeString(this.title);
        parcel.writeDouble(this.popularity);
        parcel.writeString(this.posterPath);
        parcel.writeString(this.originalLanguage);
        parcel.writeString(this.originalTitle);
        parcel.writeIntArray(this.genreIds);
        parcel.writeString(this.backdropPath);
        parcel.writeInt(this.isAdult ? 1: 0);
        parcel.writeString(this.overview);
        parcel.writeString(new SimpleDateFormat("yyyy-MM-dd").format(this.releaseDate));
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
