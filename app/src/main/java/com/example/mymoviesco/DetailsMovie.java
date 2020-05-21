package com.example.mymoviesco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.mymoviesco.MainActivity.EXTRA_MOVIE;
import static com.example.mymoviesco.MyAdapter.IMAGE_URL_BASE_PATH;

public class DetailsMovie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//display button to go back to previous page

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(EXTRA_MOVIE);

        String title = movie.getTitle();
        String originalTitle = movie.getOriginal_title();
        String posterPath = movie.getPoster_path();

        new DownloadImageTask((ImageView) findViewById(R.id.item_poster))
                .execute(IMAGE_URL_BASE_PATH + posterPath);

        TextView text_title = findViewById(R.id.item_title);
        text_title.setText(title);

        TextView text_original = findViewById(R.id.item_originalTitle);
        text_original.setText(originalTitle);

    }
}
