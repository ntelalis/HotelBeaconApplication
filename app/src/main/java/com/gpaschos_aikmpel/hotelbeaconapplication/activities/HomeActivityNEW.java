package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.CustomerServicesFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.CustomerServicesNoActiveReservationFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.DatePickerFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.LoyaltyFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.forgot_password.ForgotVerifyFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.ReservationNewFragment;

public class HomeActivityNEW extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,DatePickerFragment.DateSelected {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new);
        bottomNavigationView = findViewById(R.id.bnvHomeScreen);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.bottomNavigationRewardsProgram);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()){
            case R.id.bottomNavigationReservations:
                ReservationNewFragment reservationNewFragment = new ReservationNewFragment();
                transaction.replace(R.id.homeScreenContainer, reservationNewFragment);
                transaction.commit();
                break;
            case R.id.bottomNavigationRewardsProgram:
                LoyaltyFragment loyaltyFragment = new LoyaltyFragment();
                transaction.replace(R.id.homeScreenContainer, loyaltyFragment);
                transaction.commit();
                break;
            case R.id.bottomNavigationMyRoom:
                Reservation r = RoomDB.getInstance(this).reservationDao().getCurrentReservation();
                if(r!=null){
                    CustomerServicesFragment customerServicesFragment = new CustomerServicesFragment();
                    transaction.replace(R.id.homeScreenContainer, customerServicesFragment);
                    transaction.commit();
                }
                else {
                    CustomerServicesNoActiveReservationFragment customerServicesFragment = new CustomerServicesNoActiveReservationFragment();
                    transaction.replace(R.id.homeScreenContainer, customerServicesFragment);
                    transaction.commit();
                }

                break;
        }
        return true;
    }

    @Override
    public void pickedDate(int type, int day, int month, int year) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.homeScreenContainer);
        if(fragment instanceof ReservationNewFragment){
            ReservationNewFragment reservationNewFragment = (ReservationNewFragment) fragment;
            reservationNewFragment.pickedDate(type,day,month,year);
        }
    }
}
