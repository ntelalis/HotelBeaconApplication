package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Loyalty;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.DatePickerFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.BookFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.BookedFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.ReservationCallbacks;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.ReservationNewFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.Params;

public class NewReservationActivity extends AppCompatActivity implements DatePickerFragment.DateSelected,ReservationCallbacks{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private NavigationView.OnNavigationItemSelectedListener navigationDrawerListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.navigationDrawerReservations:
                    intent = new Intent(NewReservationActivity.this, NewReservationActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawers();
                    break;
                case R.id.navigationDrawerProfile:
                    break;
                case R.id.navigationDrawerLogout:
                    RoomDB.getInstance(NewReservationActivity.this).clearAllTables();
                    intent = new Intent(NewReservationActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.navigationDrawerContact:
                    intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+ Params.TELEPHONE));
                    startActivity(intent);
                    break;
                case R.id.navigationDrawerHome:
                    finish();
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reservation);

        drawerLayout = findViewById(R.id.reservationDrawerLayout);
        navigationView = findViewById(R.id.appNavigationDrawer);
        Customer customer = RoomDB.getInstance(this).customerDao().getCustomer();
        Loyalty loyalty = RoomDB.getInstance(this).loyaltyDao().getLoyalty();
        View hView =  navigationView.getHeaderView(0);
        TextView tvFName = hView.findViewById(R.id.tvNavigationDrawerFirstName);
        TextView tvLName = hView.findViewById(R.id.tvNavigationDrawerLastName);
        TextView tvAccNumber = hView.findViewById(R.id.tvNavigationDrawerAccountNumber);
        TextView tvTier = hView.findViewById(R.id.tvNavigationDrawerTier);
        tvFName.setText(customer.getFirstName());
        tvLName.setText(customer.getLastName());
        tvTier.setText(loyalty.getCurrentTierName());
        tvAccNumber.setText(String.valueOf(customer.getCustomerId()));
        navigationView.setNavigationItemSelectedListener(navigationDrawerListener);
        toolbar = findViewById(R.id.appToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ReservationNewFragment offerFragment = ReservationNewFragment.newInstance();
        transaction.replace(R.id.reservationContainer, offerFragment);
        transaction.commit();
    }

    @Override
    public void pickedDate(int type, int day, int month, int year) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.reservationContainer);
        if (fragment instanceof ReservationNewFragment) {
            ReservationNewFragment reservationNewFragment = (ReservationNewFragment) fragment;
            reservationNewFragment.pickedDate(type, day, month, year);
        }
    }

    @Override
    public void book(int roomTypeID, String arrival, String departure, int adults, int children) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BookFragment bookFragment = BookFragment.newInstance(roomTypeID,arrival,departure,adults,children);
        transaction.replace(R.id.reservationContainer, bookFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void showReservations() {
        finish();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(BookedFragment.TAG);
        if (fragment!=null && fragment.isVisible()) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showBooked(int reservationID) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BookedFragment bookedFragment = BookedFragment.newInstance(reservationID);
        transaction.replace(R.id.reservationContainer,bookedFragment,BookedFragment.TAG);
        transaction.commit();
    }

}