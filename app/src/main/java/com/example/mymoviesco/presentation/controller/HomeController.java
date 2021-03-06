package com.example.mymoviesco.presentation.controller;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymoviesco.data.Internet;
import com.example.mymoviesco.data.Singletons;
import com.example.mymoviesco.R;
import com.example.mymoviesco.presentation.model.Movie;
import com.example.mymoviesco.presentation.model.MovieResponse;
import com.example.mymoviesco.presentation.view.DetailsMovieActivity;
import com.example.mymoviesco.presentation.view.HomeFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mymoviesco.data.Constant.*;

public class HomeController {
    private TextView emptyMessage;
    private HomeFragment homeView;
    private View v;


    public HomeController(HomeFragment homeView, View v) {
        this.v = v;
        this.homeView = homeView;
    }

    public void onStart() {
        emptyMessage = v.findViewById(R.id.emptyMessage);
        if (Internet.isNetworkAvailable(homeView.getActivity())) {//If there is internet connection
            makeAPICall();
            emptyMessage.setVisibility(View.INVISIBLE);
        } else {
            emptyMessage.setVisibility(View.VISIBLE);
        }
    }

    public void onClickMovie(int position, List<Movie> movieList) {//Go to MovieDetailsActivity
        Intent intent = new Intent(homeView.getContext(), DetailsMovieActivity.class);
        intent.putExtra(EXTRA_MOVIE, movieList.get(position));//Send position of the movie clicked
        homeView.getContext().startActivity(intent);//change activity
    }

    private void makeAPICall() {//Get list of top movies from API
        Call<MovieResponse> call = Singletons.getMovieApiServiceInstance().getPopularMovies(API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getMovieList();
                    if (movies.size() > 0) {//If there is at least one movie
                        emptyMessage.setVisibility(View.INVISIBLE);
                        homeView.buildList(movies, v);//display the list of top movies
                    } else {
                        emptyMessage.setVisibility(View.VISIBLE);
                    }
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                showError();
            }
        });
    }

    private void showError() {
        Toast.makeText(homeView.getContext(), "API Error", Toast.LENGTH_SHORT).show();
    }

}
