package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void reserve(View view) {
        Intent intent = new Intent(this, ReservationActivity.class);
        startActivity(intent);
    }

    public void viewReservations(View view) {
        Intent intent = new Intent(this, UpcomingReservationActivity.class);
        startActivity(intent);
    }

    public void loyalty(View view) {
        Intent intent = new Intent(this, LoyaltyProgramActivity.class);
        startActivity(intent);
    }

    public void roomService(View view) {
        Intent intent = new Intent(this, RoomServiceActivity.class);
        startActivity(intent);
    }

    public void review(View view) {
        Intent intent = new Intent(this, ReviewActivity.class);
        startActivity(intent);
    }

    public void logout(View view) {
        dropAllTables(view);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void beacon(View view) {
        Intent intent = new Intent(this, MonitoringActivity.class);
        startActivity(intent);
    }

    public void dropAllTables(View view) {
        RoomDB.getInstance(this).clearAllTables();
        Toast.makeText(this, "Database cleared!", Toast.LENGTH_SHORT).show();
    }
}
