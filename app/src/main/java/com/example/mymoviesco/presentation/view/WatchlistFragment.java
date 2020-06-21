package com.example.mymoviesco.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviesco.presentation.controller.WatchlistController;
import com.example.mymoviesco.R;
import com.example.mymoviesco.presentation.model.Movie;

import java.util.List;

import static com.example.mymoviesco.data.Constant.*;

public class WatchlistFragment extends Fragment {
    private static String listStatus;
    private Menu m;
    private RecyclerView mRecyclerView;//contains recycler view created in our XML layout
    private MyAdapter mAdapter;//bridge between our data and our recycler view
    private RecyclerView.LayoutManager mLayoutManager;//aligning items in our list
    private Button deleteAll;
    private Button deleteWatched;
    private Button deleteUnwatched;
    private TextView emptyDatabase;
    private List<Movie> movieList;

    private WatchlistController controller;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_watchlist, container, false);
        getActivity().setTitle("Watchlist");
        setHasOptionsMenu(true);

        controller = new WatchlistController(this, v);
        controller.onStart();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    public void createScreen(View v){
        listStatus = MOVIE_ALL;

        deleteAll = v.findViewById(R.id.deleteAll);
        onButton_AllDeleteClickListener(deleteAll);
        deleteAll.setVisibility(View.VISIBLE);
        deleteWatched = v.findViewById(R.id.deleteWatched);
        onButton_AllWatchedClickListener(deleteWatched);
        deleteUnwatched = v.findViewById(R.id.deleteUnwatched);
        onButton_AllUnwatchedClickListener(deleteUnwatched);

        emptyDatabase = v.findViewById(R.id.emptyDatabase);

        movieList = controller.getAllMovies();
        buildList(v);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        m = menu;
        inflater.inflate(R.menu.watchlist_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        MenuItem i = m.findItem(R.id.movieStatus);
        switch (item.getItemId()) {
            case R.id.allMovies:
                movieList = controller.getAllMovies();
                i.setTitle("All");
                listStatus = MOVIE_ALL;
                break;

            case R.id.movieWatched:
                movieList = controller.getWatchedMovies();
                i.setTitle("Watched");
                listStatus = MOVIE_WATCHED;
                break;

            case R.id.movieUnwatched:
                movieList = controller.getUnwatchedMovies();
                i.setTitle("Unwatched");
                listStatus = MOVIE_UNWATCHED;
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        refreshList();
        return super.onOptionsItemSelected(item);
    }

    public void refreshList() {
        switch (listStatus) {
            case MOVIE_ALL:
                deleteAll.setVisibility(View.VISIBLE);
                deleteWatched.setVisibility(View.INVISIBLE);
                deleteUnwatched.setVisibility(View.INVISIBLE);

                movieList = controller.getAllMovies();

                break;
            case MOVIE_WATCHED:
                deleteAll.setVisibility(View.INVISIBLE);
                deleteWatched.setVisibility(View.VISIBLE);
                deleteUnwatched.setVisibility(View.INVISIBLE);

                movieList = controller.getWatchedMovies();

                break;
            case MOVIE_UNWATCHED:
                deleteAll.setVisibility(View.INVISIBLE);
                deleteWatched.setVisibility(View.INVISIBLE);
                deleteUnwatched.setVisibility(View.VISIBLE);

                movieList = controller.getUnwatchedMovies();

                break;

            default:
        }

        buildList(getView());

    }

    public void buildList(View v) {
        /*Initialization*/
        if (movieList.size() == 0) {
            emptyDatabase.setVisibility(View.VISIBLE);
            deleteAll.setVisibility(View.INVISIBLE);
            deleteWatched.setVisibility(View.INVISIBLE);
            deleteUnwatched.setVisibility(View.INVISIBLE);
            mAdapter.notifyDataSetChanged();
        } else {
            emptyDatabase.setVisibility(View.INVISIBLE);

            mRecyclerView = v.findViewById(R.id.recyclerView);
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

    public void onButton_AllDeleteClickListener(Button all_delete) {
        all_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieList = controller.onDeleteAll(mAdapter);
            }
        });
    }

    public void onButton_AllWatchedClickListener(Button all_delete) {
        all_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieList = controller.onDeleteWatched(mAdapter);
            }
        });
    }

    public void onButton_AllUnwatchedClickListener(Button all_delete) {
        all_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieList = controller.onDeleteUnwatched(mAdapter);
            }
        });
    }
}
