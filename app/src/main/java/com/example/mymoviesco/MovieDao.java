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

    @Query("SELECT * FROM Movie WHERE watched=(:watched)")
    List<Movie> getWatched(boolean watched);

    @Query("UPDATE Movie SET watched =(:watched) where id=(:id)")
    void updateMovieWatched(boolean watched, int id);

    @Insert
    void insertMovie(Movie m);

    @Delete
    void deleteMovie (Movie movie);

    @Query("DELETE FROM Movie")
    void deleteTableMovie();
}
