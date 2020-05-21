package com.example.mymoviesco;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity (tableName = "Movie")
public class Movie {
    @PrimaryKey
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    private double popularity;

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    private int vote_count;

    @ColumnInfo(name = "video")
    @SerializedName("video")
    private boolean video;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String poster_path;

    @ColumnInfo(name = "adult")
    @SerializedName("adult")
    private boolean adult;

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    private String backdrop_path;

    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    private String original_language;

    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    private String original_title;

    /*@ColumnInfo(name = "genre_ids")
    @SerializedName("genre_ids")
    private List<Integer> genre_ids= new ArrayList<Integer>();*/

    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private double vote_average;

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String overview;

    @ColumnInfo(name = "realease_date")
    @SerializedName("release_date")
    private String release_date;

    @ColumnInfo(name = "watchlist")
    private boolean watchlist;

    @ColumnInfo(name = "watched")
    private boolean watched;

    @ColumnInfo(name = "unwatched")
    private boolean unwatched;

    public Movie(double popularity, int vote_count, boolean video, String poster_path, int id, boolean adult, String backdrop_path, String original_language, String original_title, /*List<Integer> genre_ids,*/ String title, double vote_average, String overview, String release_date) {
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.video = video;
        this.poster_path = poster_path;
        this.id = id;
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.original_language = original_language;
        this.original_title = original_title;
        //this.genre_ids = genre_ids;
        this.title = title;
        this.vote_average = vote_average;
        this.overview = overview;
        this.release_date = release_date;
        watchlist = false;
        watched = false;
        unwatched = false;
    }

    public void setUnwatched(boolean unwatched) {
        this.unwatched = unwatched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public void setWatchlist(boolean watchlist) {
        this.watchlist = watchlist;
    }

    public boolean getUnwatched() {
        return unwatched;
    }

    public boolean getWatched() {
        return watched;
    }

    public boolean getWatchlist() {
        return watchlist;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    /*public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }*/

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public int getId() {
        return id;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    /*public List<Integer> getGenre_ids() {
        return genre_ids;
    }*/

    public String getTitle() {
        return title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }
}
