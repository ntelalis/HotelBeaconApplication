package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

public class CheckedInActivity extends AppCompatActivity {

    public static final String ROOM = "roomID";

    private TextView tvroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked_in);
        String room = getIntent().getStringExtra(ROOM);
        tvroom = findViewById(R.id.tvcheckedinRoom);
        tvroom.setText(room);
    }
}
