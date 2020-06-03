package com.example.mymoviesco;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.example.mymoviesco.HomeFragment.EXTRA_MOVIE;

public class WatchlistFragment extends Fragment {

    private static AppDatabase db;
    private Menu m;
    private RecyclerView mRecyclerView;//contains recycler view created in our XML layout
    private MyAdapter mAdapter;//bridge between our data and our recycler view
    private RecyclerView.LayoutManager mLayoutManager;//aligning items in our list

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_watchlist, container, false);
        getActivity().setTitle("Watchlist");

        setHasOptionsMenu(true);

        db = AppDatabase.getInstance(getContext());
        List<Movie> movieList = db.movieDao().getMovies();
        showList(movieList, v);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        m=menu;
        inflater.inflate(R.menu.watchlist_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        List<Movie> movieList;
        MenuItem i = m.findItem(R.id.movieStatus);
        switch (item.getItemId()){
            case R.id.allMovies:
                movieList = db.movieDao().getMovies();
                showList(movieList, getView());
                i.setTitle("All");
                break;

            case R.id.movieWatched:
                movieList = db.movieDao().getWatched(true);
                showList(movieList, getView());
                i.setTitle("Watched");
                break;

            case R.id.movieUnwatched:
                movieList = db.movieDao().getWatched(false);
                showList(movieList, getView());
                i.setTitle("Unwatched");
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void showList(final List<Movie> movieList, View v){
        /*Initialization*/
        mRecyclerView = v.findViewById(R.id.recyclerView);
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
