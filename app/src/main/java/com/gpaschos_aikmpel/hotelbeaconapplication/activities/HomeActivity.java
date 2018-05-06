package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gpaschos_aikmpel.hotelbeaconapplication.NotificationsFunctions.NotificationCreation;
import com.gpaschos_aikmpel.hotelbeaconapplication.NotificationsFunctions.TestNotificationsActivity;
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
        Intent intent = new Intent(this, UpcomingReservationActivity.class);
        startActivity(intent);
    }

    public void loyalty(View view){
        Intent intent = new Intent(this, LoyaltyProgramActivity.class);
        startActivity(intent);
    }

    public void roomService(View view){
        Intent intent = new Intent(this, RoomServiceActivity.class);
        startActivity(intent);
    }

    //public void notificationWelcome(View view){
    //    NotificationCreation.welcomeNotify(this);
    //}

    public void review(View view){
        Intent intent = new Intent(this, ReviewActivity.class);
        startActivity(intent);
    }

    public void notify(View view){
      //  NotificationCreation.channel(this,"testChannel","defaultChannel" );
      //  NotificationCreation.notification(this,"testChannel","Test",
          //      21,"My new Notification",R.drawable.ic_food,UpcomingReservationActivity.class);
        Intent intent = new Intent(this, TestNotificationsActivity.class);
        startActivity(intent);

    }
}
