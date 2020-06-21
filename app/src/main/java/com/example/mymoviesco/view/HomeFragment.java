package com.example.mymoviesco.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviesco.R;
import com.example.mymoviesco.controller.HomeController;
import com.example.mymoviesco.model.Movie;
import com.google.gson.GsonBuilder;

import java.util.List;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.mymoviesco.Constant.*;

public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;//contains recycler view created in our XML layout
    private MyAdapter mAdapter;//bridge between our data and our recycler view
    private RecyclerView.LayoutManager mLayoutManager;//aligning items in our list
    private HomeController controller;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Home : Top movies");

        controller = new HomeController(this,v,
                new GsonBuilder()
                .setLenient()
                .create(),
                new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                        .setLenient()
                        .create()))
                .build());
        controller.onStart();

        return v;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void showList(final List<Movie> movieList){
        /*Initialization*/
        mRecyclerView = getView().findViewById(R.id.recyclerView);
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

}
