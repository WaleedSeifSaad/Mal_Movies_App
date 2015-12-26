package com.example.wseif.malmoviesapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wseif on 12/18/2015.
 */
public class Movie implements Serializable, JsonHandler {
    private String posterPath;
    private Boolean isAdult;
    private String overview;
    private final String LOG_TAG = Movie.class.getSimpleName();

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Boolean getIsAdult() {
        return isAdult;
    }

    public void setIsAdult(Boolean isAdult) {
        this.isAdult = isAdult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(Boolean isVideo) {
        this.isVideo = isVideo;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    private Date releaseDate;
    private int id;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private String backdropPath;
    private Double popularity;
    private Integer voteCount;
    private Boolean isVideo;
    private Double voteAverage;

    @Override
    public ArrayList<JsonHandler> ParseJson(String jsonString) throws JSONException {
        final String MOVIE_ID = "id";
        final String MOVIE_ORIGINAL_TITLE = "original_title";
        final String MOVIE_POSTER_PATH = "poster_path";
        final String MOVIE_POSTER_PATH_BASE_URL = "http://image.tmdb.org/t/p/";
        final String MOVIE_POSTER_PATH_SIZE = "w185";
        final String MOVIE_OVERVIEW = "overview";
        final String MOVIE_VOTE_AVERAGE = "vote_average";
        final String MOVIE_RELEASE_DATE = "release_date";

        JSONArray movieJsonArray = new JSONArray(jsonString);
        ArrayList<JsonHandler> movieArrayList = new ArrayList<JsonHandler>();
        for (int i = 0; i < movieJsonArray.length(); i++) {
            Movie movie = new Movie();

            JSONObject movieDetails = movieJsonArray.getJSONObject(i);
            movie.setId(movieDetails.getInt(MOVIE_ID));
            movie.setOriginalTitle(movieDetails.getString(MOVIE_ORIGINAL_TITLE));
            movie.setPosterPath(MOVIE_POSTER_PATH_BASE_URL + MOVIE_POSTER_PATH_SIZE + movieDetails.getString(MOVIE_POSTER_PATH));
            movie.setOverview(movieDetails.getString(MOVIE_OVERVIEW));
            movie.setVoteAverage(movieDetails.getDouble(MOVIE_VOTE_AVERAGE));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                movie.setReleaseDate(format.parse(movieDetails.getString(MOVIE_RELEASE_DATE)));
            } catch (ParseException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
            }
            movieArrayList.add(movie);
        }
        return movieArrayList;
    }
}
