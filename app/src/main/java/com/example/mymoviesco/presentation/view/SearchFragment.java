package com.example.mymoviesco.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviesco.data.Internet;
import com.example.mymoviesco.R;
import com.example.mymoviesco.presentation.controller.SearchController;
import com.example.mymoviesco.presentation.model.Movie;

import java.util.List;

public class SearchFragment extends Fragment {

    private RecyclerView mRecyclerView;//contains recycler view created in our XML layout
    private MyAdapter mAdapter;//bridge between our data and our recycler view
    private RecyclerView.LayoutManager mLayoutManager;//aligning items in our list
    private TextView SearchStatus;
    private SearchController controller;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        getActivity().setTitle("Search");

        controller = new SearchController(this, v);

        SearchStatus = v.findViewById(R.id.SearchStatus);
        SearchStatus.setVisibility(View.VISIBLE);
        if(Internet.isNetworkAvailable(getActivity())==false){
            SearchStatus.setText("There is no internet connection :(");
        }else{
            setHasOptionsMenu(true);
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
                controller.makeAPICall(getView(), s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                SearchStatus.setVisibility(View.INVISIBLE);
                controller.makeAPICall(getView(), s);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu,inflater);
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
                controller.onClickMovie(position, movieList);
            }
        });
    }
}
