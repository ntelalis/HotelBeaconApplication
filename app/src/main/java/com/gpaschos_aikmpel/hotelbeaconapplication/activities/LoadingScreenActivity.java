package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

public class LoadingScreenActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        progressBar = findViewById(R.id.pbLoadingScreen);

    }

}
