package com.example.mymoviesco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import static com.example.mymoviesco.MainActivity.EXTRA_MOVIE;

public class Watchlist extends AppCompatActivity {

    private static AppDatabase db;
    private RecyclerView mRecyclerView;//contains recycler view created in our XML layout
    private MyAdapter mAdapter;//bridge between our data and our recycler view
    private RecyclerView.LayoutManager mLayoutManager;//aligning items in our list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        getSupportActionBar().setTitle("Watchlist");

        db = AppDatabase.getInstance(this);
        List<Movie> movieList = db.movieDao().getMovies();
        showList(movieList);

    }

    public void showList(final List<Movie> movieList){
        /*Initialization*/
        mRecyclerView = findViewById(R.id.recyclerView);
        //mRecyclerView.setHasFixedSize(true);//Recycler view doesn't change in size (Increase performance)
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MyAdapter(movieList, this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {//Open new Activity to display details of the movie
                //Give movie selected in another page
                Intent intent = new Intent(getApplicationContext(), DetailsMovie.class);
                intent.putExtra(EXTRA_MOVIE, movieList.get(position));//Send position of the movie
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.watchlist_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                Toast.makeText(this, "Click on Icon Home", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
