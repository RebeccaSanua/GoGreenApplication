package com.maiot.gogreenapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScannerActivity extends AppCompatActivity {

    ImageView ShowMap;
    ImageView ShowHome;
    ImageView ShowScanner;

    Button AvviaScanner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        AvviaScanner = findViewById(R.id.AvviaScanner);
        AvviaScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(ScannerActivity.this);
                intentIntegrator.setPrompt("Scannerizza il QR code");
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.initiateScan();
            }
        });


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null){
            String contents = intentResult.getContents();
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}