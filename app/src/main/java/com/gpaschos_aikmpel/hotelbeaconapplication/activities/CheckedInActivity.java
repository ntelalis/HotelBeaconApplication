package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.notifications.UpdateStoredVariables;

public class CheckedInActivity extends AppCompatActivity {

    public static final String ROOM = "roomID";

    private TextView tvroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked_in);
        int room = getIntent().getIntExtra(ROOM,-1);

        tvroom = findViewById(R.id.tvcheckedinRoom);
        tvroom.setText(String.valueOf(room));

        UpdateStoredVariables.checkedIn(this);
    }
}
