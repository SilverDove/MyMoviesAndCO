package com.example.mymoviesco;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.mymoviesco.HomeFragment.API_KEY;
import static com.example.mymoviesco.HomeFragment.BASE_URL;
import static com.example.mymoviesco.HomeFragment.EXTRA_MOVIE;

public class SearchFragment extends Fragment {

    private RecyclerView mRecyclerView;//contains recycler view created in our XML layout
    private MyAdapter mAdapter;//bridge between our data and our recycler view
    private RecyclerView.LayoutManager mLayoutManager;//aligning items in our list
    private TextView SearchStatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        getActivity().setTitle("Search");

        SearchStatus = v.findViewById(R.id.SearchStatus);
        SearchStatus.setVisibility(View.VISIBLE);
        if(isNetworkAvailable()==false){

            SearchStatus.setText("There is no internet connection :(");
        }else{
            setHasOptionsMenu(true);
            Toast.makeText(getContext(), "HELLO", Toast.LENGTH_SHORT).show();
            SearchStatus.setText("You can find movies by using the search bar at the top");
        }

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                SearchStatus.setVisibility(View.INVISIBLE);
                makeAPICall(getView(), s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                SearchStatus.setVisibility(View.INVISIBLE);
                makeAPICall(getView(), s);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu,inflater);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void showList(final List<Movie> movieList, View v){
        /*Initialization*/
        SearchStatus.setVisibility(View.INVISIBLE);
        mRecyclerView = v.findViewById(R.id.recyclerView);
        //mRecyclerView.setHasFixedSize(true);//Recycler view doesn't change in size (Increase performance)
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new MyAdapter(movieList, getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {//Open new Activity to display details of the movie
                //Give movie selected in another page
                Intent intent = new Intent(getContext(), DetailsMovieActivity.class);
                intent.putExtra(EXTRA_MOVIE, movieList.get(position));//Send position of the movie
                startActivity(intent);
            }
        });
    }

    private void makeAPICall(final View v, String movieSearch){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MovieApiService movieApiService = retrofit.create(MovieApiService.class);
        Call<MovieResponse> call = movieApiService.getSearchMovies(API_KEY, movieSearch);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<Movie> movies = response.body().getMovieList();
                    showList(movies, v);
                    if(movies.size() == 0){
                        SearchStatus.setText("What? No result? \nWe searched everywhere but we didn't find your request");
                        SearchStatus.setVisibility(View.VISIBLE);
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
        Toast.makeText(getContext(), "API Error", Toast.LENGTH_SHORT).show();
    }

}
