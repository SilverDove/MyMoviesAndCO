package com.example.mymoviesco.presentation.controller;

import android.content.Intent;
import android.view.View;

import com.example.mymoviesco.presentation.model.AppDatabase;
import com.example.mymoviesco.presentation.model.Movie;
import com.example.mymoviesco.presentation.view.DetailsMovieActivity;
import com.example.mymoviesco.presentation.view.MyAdapter;
import com.example.mymoviesco.presentation.view.WatchlistFragment;

import java.util.List;

import static com.example.mymoviesco.data.Constant.EXTRA_MOVIE;

public class WatchlistController {
    private static AppDatabase db;
    private List<Movie> movieList;
    private WatchlistFragment watchlistView;
    private View v;

    public WatchlistController(WatchlistFragment watchlistView, View v){
        this.watchlistView = watchlistView;
        this.db = AppDatabase.getInstance(watchlistView.getContext());
        this.v = v;
    }

    public void onStart(){
        watchlistView.createScreen(v);
    }

    public void onClickMovie(int position, List<Movie> movieList) {//See details of the selected movie
        Intent intent = new Intent(watchlistView.getContext(), DetailsMovieActivity.class);
        intent.putExtra(EXTRA_MOVIE, movieList.get(position));//Send position of the movie
        watchlistView.getContext().startActivity(intent);//Go to DetailsMovieActivity
    }

    public List<Movie> onDeleteAll(MyAdapter mAdapter){//Delete all the movies
        int size = movieList.size();
        for (int i = 0; i < size; i++) {
            mAdapter.notifyItemRemoved(0);
        }
        clearMovieList();
        db.movieDao().deleteTableMovie();//Delete all the movies from the database
        watchlistView.refreshList();//Update the list

        return movieList;
    }

    public List<Movie> onDeleteWatched(MyAdapter mAdapter){//Delete all the movies watched
        int size = movieList.size();
        for (int i = 0; i < size; i++) {
            mAdapter.notifyItemRemoved(0);
        }
        clearMovieList();
        db.movieDao().deleteMovieWatched(true);//Delete all the movies watched from the database
        movieList = db.movieDao().getWatched(true);
        watchlistView.refreshList();//refresh the list

        return movieList;
    }

    public List<Movie> onDeleteUnwatched(MyAdapter mAdapter){//Delete all the movies unwatched
        int size = db.movieDao().getNumberItems();
        for (int i = 0; i < size; i++) {
            mAdapter.notifyItemRemoved(0);
        }
        clearMovieList();
        db.movieDao().deleteMovieWatched(false);//Delete all the movies unwatched from the database
        movieList = db.movieDao().getWatched(false);
        watchlistView.refreshList();//refresh the list

        return movieList;
    }

    public List<Movie> getAllMovies(){//Get all the movies from the database
        clearMovieList();
        movieList = db.movieDao().getMovies();
        return movieList;
    }

    public List<Movie> getWatchedMovies(){//Get all the movies watched from the database
        clearMovieList();
        movieList = db.movieDao().getWatched(true);
        return movieList;
    }

    public List<Movie> getUnwatchedMovies(){//Get all the movies unwatched from the database
        clearMovieList();
        movieList = db.movieDao().getWatched(false);
        return movieList;
    }

    public void clearMovieList(){//Clear the list of movies
        if(movieList != null){
            movieList.clear();
        }
    }


}
