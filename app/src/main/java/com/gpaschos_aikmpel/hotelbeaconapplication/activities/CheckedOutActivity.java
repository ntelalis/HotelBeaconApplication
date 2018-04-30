package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

public class CheckedOutActivity extends AppCompatActivity {

    private TextView checkoutMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked_out);

        checkoutMsg = findViewById(R.id.tvcheckedoutmsg);
    }
}
