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
        if(Internet.isNetworkAvailable(getActivity())==false){//If there is no internet connection
            SearchStatus.setText(R.string.noInternet);
        }else{
            setHasOptionsMenu(true);
            SearchStatus.setText(R.string.searchHelp);
        }

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setIconified(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.setIconified(true);
                searchView.setIconified(true);
                SearchStatus.setVisibility(View.INVISIBLE);
                controller.makeAPICall(getView(), s);//Get list of movies asked by the user
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                SearchStatus.setVisibility(View.INVISIBLE);
                controller.makeAPICall(getView(), s);//Get list of movies asked by the user
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }

    public void showList(final List<Movie> movieList, View v){//Display the list of movies
        /*Initialization*/
        SearchStatus.setVisibility(View.INVISIBLE);
        mRecyclerView = v.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new MyAdapter(movieList, getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        if(movieList.size()==0){
            SearchStatus.setVisibility(View.VISIBLE);
            SearchStatus.setText(R.string.NoResults);
        }

        mAdapter.setOnItemListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {//Open new Activity to display details of the movie
                controller.onClickMovie(position, movieList);
            }
        });
    }
}
