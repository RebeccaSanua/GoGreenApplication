package com.maiot.gogreenapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class ScannerActivity extends AppCompatActivity {

    ImageView ShowMap;
    ImageView ShowHome;
    ImageView ShowScanner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        ShowHome = findViewById(R.id.ShowHome);
        ShowScanner = findViewById(R.id.ShowScanner);
        ShowMap = findViewById(R.id.ShowMap);

        final AnimatorSet clickAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.click_animation);

        ShowHome.setOnClickListener(view -> {
            ShowHome.setPivotX(ShowHome.getWidth() / 2);
            ShowHome.setPivotY(ShowHome.getHeight() / 2);
            clickAnimation.setTarget(ShowHome);
            clickAnimation.start();
            Intent intent = new Intent(ScannerActivity.this, MainActivity.class);
            startActivity(intent);
        });

        ShowScanner.setOnClickListener(view -> {
            ShowScanner.setPivotX(ShowScanner.getWidth() / 2);
            ShowScanner.setPivotY(ShowScanner.getHeight() / 2);
            clickAnimation.setTarget(ShowScanner);
            clickAnimation.start();
            Intent intent = new Intent(ScannerActivity.this, ScannerActivity.class);
            startActivity(intent);
        });

        ShowMap.setOnClickListener(view -> {
            ShowMap.setPivotX(ShowMap.getWidth() / 2);
            ShowMap.setPivotY(ShowMap.getHeight() / 2);
            clickAnimation.setTarget(ShowMap);
            clickAnimation.start();
            Intent intent = new Intent(ScannerActivity.this, MapsActivity.class);
            startActivity(intent);
        });

    }




}