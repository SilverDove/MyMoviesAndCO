package com.example.mymoviesco.controller;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymoviesco.Singletons;
import com.example.mymoviesco.R;
import com.example.mymoviesco.model.Movie;
import com.example.mymoviesco.model.MovieResponse;
import com.example.mymoviesco.view.HomeFragment;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.mymoviesco.Constant.*;

public class HomeController {
    private TextView emptyMessage;
    private HomeFragment homeView;
    private Gson gson;
    private Retrofit retrofit;
    private View v;


    public HomeController(HomeFragment homeView, View v, Gson gson){
        this.gson = gson;
        this.retrofit =retrofit;
        this.v = v;
        this.homeView = homeView;

    }

    public void onStart(){
        emptyMessage = v.findViewById(R.id.emptyMessage);

        if(homeView.isNetworkAvailable()){
            makeAPICall();
            System.out.println("INTERNET");
        }else{
            emptyMessage.setVisibility(View.VISIBLE);
            System.out.println("NO INTERNET");
        }
    }

    private void makeAPICall(){
        Call<MovieResponse> call = Singletons.getMovieApiServiceInstance().getPopularMovies(API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<Movie> movies = response.body().getMovieList();
                    if (movies.size()>0){
                        emptyMessage.setVisibility(View.INVISIBLE);
                        homeView.showList(movies);
                    }else{
                        emptyMessage.setVisibility(View.VISIBLE);
                    }
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
        Toast.makeText(homeView.getContext(), "API Error", Toast.LENGTH_SHORT).show();
    }

}
