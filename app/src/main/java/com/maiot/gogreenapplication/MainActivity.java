package com.maiot.gogreenapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
//importo la classe Button
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    ImageView ShowMap1;
    ImageView ShowHome;
    ImageView ShowScanner;
    Button ShowMap2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShowHome = findViewById(R.id.ShowHome);
        ShowScanner = findViewById(R.id.ShowScanner);
        ShowMap1 = findViewById(R.id.ShowMap1);
        ShowMap2 = findViewById(R.id.ShowMap2);

        final AnimatorSet clickAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.click_animation);

        ShowHome.setOnClickListener(view -> {
            ShowHome.setPivotX(ShowHome.getWidth() / 2);
            ShowHome.setPivotY(ShowHome.getHeight() / 2);
            clickAnimation.setTarget(ShowHome);
            clickAnimation.start();
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        });

        ShowScanner.setOnClickListener(view -> {
            ShowScanner.setPivotX(ShowScanner.getWidth() / 2);
            ShowScanner.setPivotY(ShowScanner.getHeight() / 2);
            clickAnimation.setTarget(ShowScanner);
            clickAnimation.start();
            Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
            startActivity(intent);
        });

        ShowMap1.setOnClickListener(view -> {
            ShowMap1.setPivotX(ShowMap1.getWidth() / 2);
            ShowMap1.setPivotY(ShowMap1.getHeight() / 2);
            clickAnimation.setTarget(ShowMap1);
            clickAnimation.start();
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        });

        ShowMap2.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        });

    }




}