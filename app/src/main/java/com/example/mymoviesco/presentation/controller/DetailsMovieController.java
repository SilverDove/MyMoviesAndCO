package com.example.mymoviesco.presentation.controller;

import android.content.Intent;
import android.view.View;

import com.example.mymoviesco.presentation.model.AppDatabase;
import com.example.mymoviesco.presentation.model.Movie;
import com.example.mymoviesco.presentation.view.DetailsMovieActivity;

import java.util.List;

import static com.example.mymoviesco.data.Constant.EXTRA_MOVIE;

public class DetailsMovieController {
    private DetailsMovieActivity detailsMovieView;
    private AppDatabase db;
    private Movie movie;
    private boolean watchlist;

    public DetailsMovieController(DetailsMovieActivity detailsMovieView, AppDatabase db){
        this.detailsMovieView = detailsMovieView;
        this.db = db;
    }

    public void onStart(){
        Intent intent = detailsMovieView.getIntent();
        Movie m = intent.getParcelableExtra(EXTRA_MOVIE);
        this.movie = m;

        this.watchlist = checkMovieAlreadyInWatchlist();

        detailsMovieView.BuildDetailsPage(movie);//Display page with all the information
    }

    public boolean getWatchlistStatus(){
        return this.watchlist;
    }

    public void setWatchlistStatus(boolean b){
        this.watchlist = b;
    }

    public Movie getCurrentMovie(){
        return movie;
    }

    public boolean checkMovieAlreadyInWatchlist(){//Check if the current movie is already in the watchlist
        List<Movie> movieList = db.movieDao().getMovies();

        for (int i=0; i<movieList.size(); i++){
            if(movieList.get(i).getId() == movie.getId()){//The movie is already in the watchlist
                return true;
            }
        }
        return false;//The movie is not in the watchlist
    }

    public void updateToMovieWatched(){
        db.movieDao().updateMovieWatched(true,movie.getId());
    }

    public void updateToMovieUnwatched(){
        db.movieDao().updateMovieWatched(false,movie.getId());
    }

    public void insertMovie(){
        db.movieDao().insertMovie(movie);
        setWatchlistStatus(true);
    }

    public void removeMovie(){
        db.movieDao().deleteMovie(movie);
        setWatchlistStatus(false);
    }
}
