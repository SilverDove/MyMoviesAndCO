package com.example.mymoviesco.presentation.controller;

import android.content.Intent;

import com.example.mymoviesco.presentation.model.AppDatabase;
import com.example.mymoviesco.presentation.model.Movie;
import com.example.mymoviesco.presentation.view.DetailsMovieActivity;

import java.util.List;

import static com.example.mymoviesco.data.Constant.EXTRA_MOVIE;

public class DetailsMovieController {
    private DetailsMovieActivity detailsMovieView; //Instance of the DetailsMovieActivity
    private AppDatabase db;//Instance of the database
    private Movie movie; //Current movie displayed on the screen
    private boolean watchlist;// Status of the current movie (in the watchlist or not)

    public DetailsMovieController(DetailsMovieActivity detailsMovieView, AppDatabase db){
        this.detailsMovieView = detailsMovieView;
        this.db = db;
    }

    public void onStart(){
        // --- Get information about the item clicked in RecyclerView ---
        Intent intent = detailsMovieView.getIntent();
        Movie m = intent.getParcelableExtra(EXTRA_MOVIE);
        this.movie = m;

        this.watchlist = checkMovieAlreadyInWatchlist();//Check if the current movie is already in the database

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
        db.movieDao().updateMovieWatched(true,movie.getId());//The user whatched this movie
    }

    public void updateToMovieUnwatched(){
        db.movieDao().updateMovieWatched(false,movie.getId());//The user didn't watch this movie
    }

    public void insertMovie(){//Insert the movie into the database
        db.movieDao().insertMovie(movie);
        setWatchlistStatus(true);
    }

    public void removeMovie(){//remove the movie into the watchlist
        db.movieDao().deleteMovie(movie);
        setWatchlistStatus(false);
    }
}
