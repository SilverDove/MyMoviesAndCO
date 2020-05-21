package com.example.mymoviesco;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM Movie")
    List<Movie> getMovies();

    @Query("SELECT * FROM Movie WHERE watchlist = (:watchlist)")
    List<Movie> getWatchlist(boolean watchlist);

    @Query("SELECT * FROM Movie WHERE watchlist =(:watchlist) AND watched=(:watched)")
    List<Movie> getWatched(boolean watchlist, boolean watched);

    @Insert
    void insertMovie(Movie m);

    @Update
    void updateMovie(Movie m);

    @Query("DELETE FROM Movie WHERE id = :movieID")
    void deleteMovie (int movieID);

    @Query("DELETE FROM Movie")
    void deleteTableMovie();
}
