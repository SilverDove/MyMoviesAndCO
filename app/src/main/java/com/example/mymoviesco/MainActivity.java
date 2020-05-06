package com.example.mymoviesco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;//contains recycler view created in our XML layout
    private RecyclerView.Adapter mAdapter;//bridge between our data and our recycler view
    private RecyclerView.LayoutManager mLayoutManager;//aligning items in our list

    static final String BASE_URL = "https://api.themoviedb.org/3/";
    private final static String API_KEY = "7b570a518d203152ccc9be5b1e0d0388";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeAPICall();
    }

    public void showList(List<Movie> movieList){
        /*Initialization*/
        mRecyclerView = findViewById(R.id.recyclerView);
        //mRecyclerView.setHasFixedSize(true);//Recycler view doesn't change in size (Increase performance)
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MyAdapter(movieList, this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void makeAPICall(){

        Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

        Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        MovieApiService movieApiService = retrofit.create(MovieApiService.class);

        Call<MovieResponse> call = movieApiService.getPopularMovies(API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<Movie> movies = response.body().getMovieList();
                    showList(movies);
                }else {
                    if (response.body() == null){
                        Toast.makeText(getApplicationContext(), "response.body() is null", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Error inside onResponse", Toast.LENGTH_SHORT).show();
                    }

                    //showError();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                    showError();
                    System.out.println("ERROR IS : "+t);
            }
        });
    }

    private void showError(){
        Toast.makeText(this, "API Error", Toast.LENGTH_SHORT).show();
    }
}
