package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void reserve(View view){
        Intent intent = new Intent(this,ReservationActivity.class);
        intent.putExtra("customerid",getIntent().getStringExtra("customerid"));
        startActivity(intent);
    }
}
