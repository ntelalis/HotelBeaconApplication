package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gpaschos_aikmpel.hotelbeaconapplication.Notifications;
import com.gpaschos_aikmpel.hotelbeaconapplication.R;

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

    public void viewReservations(View view){
        Intent intent = new Intent(this, ViewReservationsActivity.class);
        startActivity(intent);
    }

    public void roomService(View view){
        Intent intent = new Intent(this, RoomServiceActivity.class);
        startActivity(intent);
    }

    public void notificationWelcome(View view){
        Notifications.welcomeNotify(this);
    }

}
