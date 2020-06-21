package com.example.mymoviesco.data;

import com.example.mymoviesco.presentation.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiService {
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies (@Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieResponse> getSearchMovies(@Query("api_key") String apiKey, @Query("query") String movie);
}