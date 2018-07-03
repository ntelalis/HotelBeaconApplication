package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.MyRoomActiveFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.MyRoomInactiveFragment;

public class CustomerServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_services);

        Reservation current = RoomDB.getInstance(this).reservationDao().getCurrentReservation();
        if (current != null && current.isCheckedInNotCheckedOut()) {
           // String checkIn = RoomDB.getInstance(this).reservationDao().getCurrentReservation().getCheckIn();
            int reservationID = RoomDB.getInstance(this).reservationDao().getCurrentReservation().getId();
            int roomNo = RoomDB.getInstance(this).reservationDao().getCurrentReservation().getRoomNumber();
            int floor = RoomDB.getInstance(this).reservationDao().getCurrentReservation().getRoomFloor();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            MyRoomActiveFragment fragment = MyRoomActiveFragment.newInstance(reservationID, roomNo, floor);
            transaction.replace(R.id.flCustomerServicesContainer, fragment);
            transaction.commit();
        }
        else{
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            MyRoomInactiveFragment fragment = MyRoomInactiveFragment.newInstance();
            transaction.replace(R.id.flCustomerServicesContainer, fragment);
            transaction.commit();
        }

    }

}
