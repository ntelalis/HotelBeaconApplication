package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.CheckOutFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.CustomerServicesFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.CustomerServicesNoActiveReservationFragment;

public class CustomerServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_services);

        Reservation current = RoomDB.getInstance(this).reservationDao().getCurrentReservation();
        if (current != null && current.isCheckedInNotCheckedOut()) {
            String checkIn = RoomDB.getInstance(this).reservationDao().getCurrentReservation().getCheckIn();
            int reservationID = RoomDB.getInstance(this).reservationDao().getCurrentReservation().getId();
            int roomNo = RoomDB.getInstance(this).reservationDao().getCurrentReservation().getRoomNumber();
            int floor = RoomDB.getInstance(this).reservationDao().getCurrentReservation().getRoomFloor();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            CustomerServicesFragment fragment = CustomerServicesFragment.newInstance(checkIn, reservationID, roomNo, floor);
            transaction.replace(R.id.flCustomerServicesContainer, fragment);
            transaction.commit();
        }
        else{
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            CustomerServicesNoActiveReservationFragment fragment = CustomerServicesNoActiveReservationFragment.newInstance();
            transaction.replace(R.id.flCustomerServicesContainer, fragment);
            transaction.commit();
        }

    }

}
