package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity implements JsonListener{

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
        Intent intent = new Intent(this, UpcomingReservationsWithFragmentsActivity.class);
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
        RoomDB.getInstance(this).clearAllTables();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void clearServerDatabse(View view){
        VolleyQueue.getInstance(this).jsonRequest(this, URL.deleteUrl,null);
    }

    public void doorUnlock(View v){
        Intent intent = new Intent(this, DoorUnlockActivity.class);
        startActivity(intent);
    }

    public void myRoom(View view){
        Intent intent = new Intent(this, CustomerServicesActivity.class);
        startActivity(intent);
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) {
        Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
    }
}
