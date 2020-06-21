package com.example.mymoviesco.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mymoviesco.presentation.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM Movie")
    List<Movie> getMovies();//Get all the movies from the database

    @Query("SELECT * FROM Movie WHERE watched=(:watched)")
    List<Movie> getWatched(boolean watched);//Get only the movies watched

    @Query("UPDATE Movie SET watched =(:watched) where id=(:id)")
    void updateMovieWatched(boolean watched, int id);//Update status of a movie

    @Insert
    void insertMovie(Movie m);//Insert a movie into the database

    @Delete
    void deleteMovie (Movie movie);//Delete a movie from the database

    @Query("DELETE FROM Movie")
    void deleteTableMovie();//Delete all the movies from the database

    @Query("SELECT COUNT(*) FROM Movie")//Give the number of movies in the database
    int getNumberItems();

    @Query("DELETE FROM Movie WHERE watched=(:watched)")//Delete movies with a specific status
    void deleteMovieWatched(boolean watched);

}
