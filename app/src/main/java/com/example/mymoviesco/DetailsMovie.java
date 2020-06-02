package com.example.mymoviesco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static com.example.mymoviesco.HomeFragment.EXTRA_MOVIE;
import static com.example.mymoviesco.MyAdapter.IMAGE_URL_BASE_PATH;

public class DetailsMovie extends AppCompatActivity {

    private AppDatabase db;
    private Movie movie;
    private boolean watchlist;
    private Menu menu;
    private Switch simpleSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);

        Intent intent = getIntent();
        movie = intent.getParcelableExtra(EXTRA_MOVIE);

        getSupportActionBar().setTitle(movie.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//display button to go back to previous page

        simpleSwitch = (Switch) findViewById(R.id.simpleSwitch);
        db = AppDatabase.getInstance(this);
        watchlist = checkMovieAlreadyInWatchlist();//Check if the movie is already in the watchlist

        SwitchActionListener();
        BuildDetailsPage();//Display page with all the information

    }

    public void SwitchActionListener(){
        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //TODO: check when button is clicked
                if(simpleSwitch.isChecked()){//If the switch is 'On'
                    simpleSwitch.setText("Watched");
                    db.movieDao().updateMovieWatched(true,movie.getId());
                }else{
                    simpleSwitch.setText("Unwatched");
                    db.movieDao().updateMovieWatched(false,movie.getId());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu,menu);
        if(watchlist == true) {//If the movie is already in the watchlist
            menu.findItem(R.id.watchlist).setIcon(R.drawable.ic_playlist_add_check);
        }else{
            menu.findItem(R.id.watchlist).setIcon(R.drawable.ic_playlist_add);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //TODO: check button clicked
        switch (item.getItemId()){
            case R.id.watchlist:
                if(watchlist==false){//If the movie is not in the watchlist
                    db.movieDao().insertMovie(movie);
                    item.setIcon(R.drawable.ic_playlist_add_check);
                    simpleSwitch.setVisibility(View.VISIBLE);
                    watchlist=true;
                }else{//Otherwise
                    db.movieDao().deleteMovie(movie);
                    item.setIcon(R.drawable.ic_playlist_add);
                    simpleSwitch.setVisibility(View.INVISIBLE);
                    watchlist=false;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean checkMovieAlreadyInWatchlist(){//Check if the current movie is already in the watchlist
        List<Movie> movieList = db.movieDao().getMovies();

        for (int i=0; i<movieList.size(); i++){
            if(movieList.get(i).getId() == movie.getId()){//The movie is already in the watchlist
                return true;
            }
        }
        return false;//The movie is not in the watchlist
    }

    private void BuildDetailsPage(){
        if(checkMovieAlreadyInWatchlist()){//If the movie is in the watchlist
            simpleSwitch.setVisibility(View.VISIBLE);
            if(watchlist == true && movie.getWatched()==true){//If the movie is in the database and watched
                simpleSwitch.setText("Watched");
                simpleSwitch.setChecked(true);
            }else{
                simpleSwitch.setText("Unwatched");
                simpleSwitch.setChecked(false);
            }
        }else{
            simpleSwitch.setVisibility(View.INVISIBLE);
        }

        //Display movie information
        String title = movie.getTitle();
        String originalTitle = movie.getOriginal_title();
        String overview = movie.getOverview();
        String backdropPath = movie.getBackdrop_path();
        String releaseDate = movie.getRelease_date();
        String popularity = Double.toString(movie.getPopularity());
        String voteAverage = Double.toString(movie.getVote_average());
        String voteCount = Integer.toString(movie.getVote_count());

        new DownloadImageTask((ImageView) findViewById(R.id.item_backdrop))
                .execute(IMAGE_URL_BASE_PATH + backdropPath);

        TextView text_title = findViewById(R.id.item_title);
        text_title.setText(title);

        TextView text_original = findViewById(R.id.item_originalTitle);
        text_original.setText(originalTitle);

        TextView text_date = findViewById(R.id.item_release_date);
        text_date.setText(releaseDate);

        TextView text_overview = findViewById(R.id.item_overview);
        text_overview.setText(overview);

        TextView text_popularity = findViewById(R.id.item_popularity);
        text_popularity.setText(popularity);

        TextView text_average = findViewById(R.id.item_vote_average);
        text_average.setText(voteAverage);

        TextView text_count = findViewById(R.id.item_vote_count);
        text_count.setText(voteCount);

    }
}
