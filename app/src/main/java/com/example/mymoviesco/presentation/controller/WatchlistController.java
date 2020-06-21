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

    public void onClickMovie(int position, List<Movie> movieList) {
        //Give movie selected in another page
        Intent intent = new Intent(watchlistView.getContext(), DetailsMovieActivity.class);
        intent.putExtra(EXTRA_MOVIE, movieList.get(position));//Send position of the movie
        watchlistView.getContext().startActivity(intent);
    }

    public List<Movie> onDeleteAll(MyAdapter mAdapter){
        int size = movieList.size();
        for (int i = 0; i < size; i++) {
            mAdapter.notifyItemRemoved(0);
        }
        clearMovieList();
        db.movieDao().deleteTableMovie();
        watchlistView.refreshList();

        return movieList;
    }

    public List<Movie> onDeleteWatched(MyAdapter mAdapter){
        int size = movieList.size();
        for (int i = 0; i < size; i++) {
            mAdapter.notifyItemRemoved(0);
        }
        clearMovieList();
        db.movieDao().deleteMovieWatched(true);
        movieList = db.movieDao().getWatched(true);
        watchlistView.refreshList();

        return movieList;
    }

    public List<Movie> onDeleteUnwatched(MyAdapter mAdapter){
        int size = db.movieDao().getNumberItems();
        for (int i = 0; i < size; i++) {
            mAdapter.notifyItemRemoved(0);
        }
        clearMovieList();
        db.movieDao().deleteMovieWatched(false);
        movieList = db.movieDao().getWatched(false);
        watchlistView.refreshList();

        return movieList;
    }

    public List<Movie> getAllMovies(){
        clearMovieList();
        movieList = db.movieDao().getMovies();
        return movieList;
    }

    public List<Movie> getWatchedMovies(){
        clearMovieList();
        movieList = db.movieDao().getWatched(true);
        return movieList;
    }

    public List<Movie> getUnwatchedMovies(){
        clearMovieList();
        movieList = db.movieDao().getWatched(false);
        return movieList;
    }

    public void clearMovieList(){
        if(movieList != null){
            movieList.clear();
        }
    }


}
