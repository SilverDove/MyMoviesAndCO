package com.example.mymoviesco.controller;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.mymoviesco.MovieApiService;
import com.example.mymoviesco.Singletons;
import com.example.mymoviesco.model.Movie;
import com.example.mymoviesco.model.MovieResponse;
import com.example.mymoviesco.view.DetailsMovieActivity;
import com.example.mymoviesco.view.SearchFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.mymoviesco.Constant.API_KEY;
import static com.example.mymoviesco.Constant.BASE_URL;
import static com.example.mymoviesco.Constant.EXTRA_MOVIE;

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
