package com.example.mymoviesco.presentation.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.mymoviesco.R;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(5000)//5 seconds
                .withBackgroundColor(Color.parseColor("#f0a500"))
                .withAfterLogoText(getString(R.string.Version))
                .withFooterText(getString(R.string.TMDB_Credits))
                .withBeforeLogoText(getString(R.string.welcomeMessage))
                .withLogo(R.drawable.cinema3);

        config.getFooterTextView().setTextColor(Color.WHITE);
        config.getBeforeLogoTextView().setTextColor(Color.WHITE);
        config.getAfterLogoTextView().setTextColor(Color.WHITE);
        config.getAfterLogoTextView().setTextSize(12);
        config.getFooterTextView().setTextSize(13);
        config.getBeforeLogoTextView().setTextSize(20);
        config.getLogo().setMaxHeight(800);
        config.getLogo().setMaxWidth(800);

        View easySplashCreen = config.create();//Create Splash Screen
        setContentView(easySplashCreen);
    }
}
