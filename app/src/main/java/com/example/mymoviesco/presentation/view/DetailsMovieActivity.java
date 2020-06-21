package com.example.mymoviesco.presentation.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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


import com.example.mymoviesco.presentation.controller.DetailsMovieController;
import com.example.mymoviesco.presentation.model.AppDatabase;
import com.example.mymoviesco.R;
import com.example.mymoviesco.presentation.model.Movie;

import static com.example.mymoviesco.presentation.view.MyAdapter.IMAGE_URL_BASE_PATH;

public class DetailsMovieActivity extends AppCompatActivity {
    private DetailsMovieController controller;
    private Menu menu;
    private Switch simpleSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//display button to go back to previous page

        simpleSwitch = findViewById(R.id.simpleSwitch);

        controller = new DetailsMovieController(this, AppDatabase.getInstance(this));
        controller.onStart();

        SwitchActionListener();

    }

    public void SwitchActionListener(){
        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(simpleSwitch.isChecked()){//If the switch is 'On'
                    simpleSwitch.setText("Watched");
                    controller.updateToMovieWatched();
                }else{
                    simpleSwitch.setText("Unwatched");
                    controller.updateToMovieUnwatched();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu,menu);
        if(controller.getWatchlistStatus() == true) {//If the movie is already in the watchlist
            menu.findItem(R.id.watchlist).setIcon(R.drawable.ic_playlist_add_check);
        }else{
            menu.findItem(R.id.watchlist).setIcon(R.drawable.ic_playlist_add);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.watchlist:
                if(controller.getWatchlistStatus()==false){//If the movie is not in the watchlist
                    //add the movie into the database and change the icon
                    controller.insertMovie();
                    item.setIcon(R.drawable.ic_playlist_add_check);
                    Toast.makeText(this, controller.getCurrentMovie().getTitle()+" added in your watchlist", Toast.LENGTH_SHORT).show();
                }else{//Otherwise
                    //remove the movie into the database and change the icon
                    controller.removeMovie();
                    item.setIcon(R.drawable.ic_playlist_add);
                    simpleSwitch.setVisibility(View.INVISIBLE);
                    Toast.makeText(this, controller.getCurrentMovie().getTitle()+" removed from your watchlist", Toast.LENGTH_SHORT).show();
                }
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void BuildDetailsPage(Movie movie){
        if(controller.checkMovieAlreadyInWatchlist()){//If the movie is in the watchlist
            simpleSwitch.setVisibility(View.VISIBLE);
            if(controller.getWatchlistStatus() == true && movie.getWatched()==true){//If the movie is in the database and watched
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
