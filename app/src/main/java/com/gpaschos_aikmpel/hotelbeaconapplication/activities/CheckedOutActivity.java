package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.notifications.UpdateStoredVariables;

public class CheckedOutActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked_out);

        UpdateStoredVariables.checkedOut(this);

    }
}
