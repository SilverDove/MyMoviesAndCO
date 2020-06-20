package com.example.mymoviesco;

import android.content.Intent;
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

import java.util.List;

import static com.example.mymoviesco.HomeFragment.EXTRA_MOVIE;

public class WatchlistFragment extends Fragment {

    public static final String MOVIE_ALL = "com.example.mymoviesco.MOVIE_ALL";
    public static final String MOVIE_WATCHED = "com.example.mymoviesco.MOVIE_WATCHED";
    public static final String MOVIE_UNWATCHED = "com.example.mymoviesco.MOVIE_UNWATCHED";

    private static String listStatus;
    private static AppDatabase db;
    private Menu m;
    private RecyclerView mRecyclerView;//contains recycler view created in our XML layout
    private MyAdapter mAdapter;//bridge between our data and our recycler view
    private RecyclerView.LayoutManager mLayoutManager;//aligning items in our list
    private Button deleteAll;
    private Button deleteWatched;
    private Button deleteUnwatched;
    private TextView emptyDatabase;
    private List<Movie> movieList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_watchlist, container, false);
        getActivity().setTitle("Watchlist");

        setHasOptionsMenu(true);

        listStatus = MOVIE_ALL;

        deleteAll = v.findViewById(R.id.deleteAll);
        onButton_AllDeleteClickListener(deleteAll);
        deleteAll.setVisibility(View.VISIBLE);
        deleteWatched = v.findViewById(R.id.deleteWatched);
        onButton_AllWatchedClickListener(deleteWatched);
        deleteUnwatched = v.findViewById(R.id.deleteUnwatched);
        onButton_AllUnwatchedClickListener(deleteUnwatched);

        emptyDatabase = v.findViewById(R.id.emptyDatabase);

        db = AppDatabase.getInstance(getContext());
        movieList = db.movieDao().getMovies();

        buildList(v);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println(listStatus);
        refreshList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        m=menu;
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

        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        MenuItem i = m.findItem(R.id.movieStatus);
        switch (item.getItemId()){
            case R.id.allMovies:
                movieList.clear();
                movieList = db.movieDao().getMovies();
                i.setTitle("All");
                listStatus = MOVIE_ALL;

                refreshList();

                break;

            case R.id.movieWatched:
                movieList.clear();
                movieList = db.movieDao().getWatched(true);
                i.setTitle("Watched");
                listStatus = MOVIE_WATCHED;

                refreshList();

                break;

            case R.id.movieUnwatched:
                movieList.clear();
                movieList = db.movieDao().getWatched(false);
                i.setTitle("Unwatched");
                listStatus = MOVIE_UNWATCHED;

                refreshList();

                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshList(){
        switch(listStatus){
            case MOVIE_ALL:
                deleteAll.setVisibility(View.VISIBLE);
                deleteWatched.setVisibility(View.INVISIBLE);
                deleteUnwatched.setVisibility(View.INVISIBLE);

                if(movieList != null){
                    movieList.clear();
                }
                movieList = db.movieDao().getMovies();

                break;
            case MOVIE_WATCHED:
                deleteAll.setVisibility(View.INVISIBLE);
                deleteWatched.setVisibility(View.VISIBLE);
                deleteUnwatched.setVisibility(View.INVISIBLE);

                if(movieList != null){
                    movieList.clear();
                }
                movieList = db.movieDao().getWatched(true);

                break;
            case MOVIE_UNWATCHED:
                deleteAll.setVisibility(View.INVISIBLE);
                deleteWatched.setVisibility(View.INVISIBLE);
                deleteUnwatched.setVisibility(View.VISIBLE);

                if(movieList != null){
                    movieList.clear();
                }
                movieList = db.movieDao().getWatched(false);

                break;

            default:
        }

        buildList(getView());

    }

    public void buildList(View v){
        /*Initialization*/
        if(movieList.size()==0){
            emptyDatabase.setVisibility(View.VISIBLE);
            deleteAll.setVisibility(View.INVISIBLE);
            deleteWatched.setVisibility(View.INVISIBLE);
            deleteUnwatched.setVisibility(View.INVISIBLE);
            mAdapter.notifyDataSetChanged();
        }else{
            System.out.println("Hello, My list size is "+ movieList.size());
            emptyDatabase.setVisibility(View.INVISIBLE);

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

    public void onButton_AllDeleteClickListener(Button all_delete){
        all_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = db.movieDao().getNumberItems();
                for(int i=0; i<size; i++){
                    mAdapter.notifyItemRemoved(0);
                }
                movieList.clear();
                db.movieDao().deleteTableMovie();
                //deleteAll.setVisibility(View.INVISIBLE);
                refreshList();

            }
        });
    }

    public void onButton_AllWatchedClickListener(Button all_delete){
        all_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = movieList.size();
                for(int i=0; i<size; i++){
                    mAdapter.notifyItemRemoved(0);
                }
                movieList.clear();
                db.movieDao().deleteMovieWatched(true);
                movieList = db.movieDao().getWatched(true);
                refreshList();
                //deleteWatched.setVisibility(View.INVISIBLE);

            }
        });
    }

    public void onButton_AllUnwatchedClickListener(Button all_delete){
        all_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = db.movieDao().getNumberItems();
                for(int i=0; i<size; i++){
                    mAdapter.notifyItemRemoved(0);
                }
                movieList.clear();
                db.movieDao().deleteMovieWatched(false);
                movieList = db.movieDao().getWatched(false);
                refreshList();
                //deleteUnwatched.setVisibility(View.INVISIBLE);

            }
        });
    }
}
