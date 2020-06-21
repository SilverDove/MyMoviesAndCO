package com.example.mymoviesco;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.mymoviesco.Constant.BASE_URL;

public class Singletons {

    private static Gson gsonInstance;
    private static MovieApiService movieApiServiceInstance;

    public static Gson getGsonInstance(){
        if(gsonInstance == null){
            gsonInstance = new GsonBuilder()
                    .setLenient()
                    .create();
        }
        return gsonInstance;
    }

    public static MovieApiService getMovieApiServiceInstance() {
        if(movieApiServiceInstance == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGsonInstance()))
                    .build();

            movieApiServiceInstance = retrofit.create(MovieApiService.class);

        }
        return movieApiServiceInstance;
    }
}
