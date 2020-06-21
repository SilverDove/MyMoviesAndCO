package com.example.mymoviesco.presentation.controller;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.mymoviesco.data.Singletons;
import com.example.mymoviesco.presentation.model.Movie;
import com.example.mymoviesco.presentation.model.MovieResponse;
import com.example.mymoviesco.presentation.view.DetailsMovieActivity;
import com.example.mymoviesco.presentation.view.SearchFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mymoviesco.data.Constant.API_KEY;
import static com.example.mymoviesco.data.Constant.EXTRA_MOVIE;

public class SearchController {
    private SearchFragment searchView;
    private View v;

    public SearchController(SearchFragment searchView, View v){
        this.searchView = searchView;
        this.v = v;
    }

    public void onClickMovie(int position, List<Movie> movieList) {
        //Give movie selected in another page
        Intent intent = new Intent(searchView.getContext(), DetailsMovieActivity.class);
        intent.putExtra(EXTRA_MOVIE, movieList.get(position));//Send position of the movie
        searchView.getContext().startActivity(intent);
    }

    public void makeAPICall(final View v, String movieSearch){

        Call<MovieResponse> call = Singletons.getMovieApiServiceInstance().getSearchMovies(API_KEY, movieSearch);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<Movie> movies = response.body().getMovieList();
                    searchView.showList(movies, v);
                }else {
                    showError();

                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                showError();
            }
        });
    }

    private void showError(){
        Toast.makeText(searchView.getContext(), "API Error", Toast.LENGTH_SHORT).show();
    }


}
