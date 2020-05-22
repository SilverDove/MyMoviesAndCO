package com.example.mymoviesco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.mymoviesco.MainActivity.EXTRA_MOVIE;
import static com.example.mymoviesco.MyAdapter.IMAGE_URL_BASE_PATH;

public class DetailsMovie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(EXTRA_MOVIE);

        getSupportActionBar().setTitle(movie.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//display button to go back to previous page

        BuildDetailsPage(movie);//Display page with all the information

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.watchlist:
                Toast.makeText(this, "Click on Icon Watchlist", Toast.LENGTH_SHORT).show();
                //add movie into the database + change icon
                //remove from the database if already added + change icon
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void BuildDetailsPage(Movie movie){
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
