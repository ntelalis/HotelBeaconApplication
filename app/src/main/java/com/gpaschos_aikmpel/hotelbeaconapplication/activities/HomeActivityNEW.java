package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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


import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.adapters.MyReservationsAdapter;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomType;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.CustomerServicesFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.CustomerServicesNoActiveReservationFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.DatePickerFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.LoyaltyFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.ReservationCallbacks;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.ReservationNewFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.UpcomingReservationNoneFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.UpcomingReservationRecyclerViewFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HomeActivityNEW extends AppCompatActivity implements DatePickerFragment.DateSelected, ReservationCallbacks {

    private Fragment fragmentToSet = null;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private NavigationView.OnNavigationItemSelectedListener navigationDrawerListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigationDrawerReservations:
                    fragmentToSet = ReservationNewFragment.newInstance();
                    drawerLayout.closeDrawers();
                    break;
                case R.id.navigationDrawerProfile:
                    break;
                case R.id.navigationDrawerLogout:
                    RoomDB.getInstance(HomeActivityNEW.this).clearAllTables();
                    Intent intent = new Intent(HomeActivityNEW.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    break;
            }
            return true;
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()) {
                case R.id.bottomNavigationReservations:
                    myReservations();
                    break;
                case R.id.bottomNavigationRewardsProgram:
                    LoyaltyFragment loyaltyFragment = new LoyaltyFragment();
                    transaction.replace(R.id.homeScreenContainer, loyaltyFragment);
                    transaction.commit();
                    break;
                case R.id.bottomNavigationMyRoom:
                    Reservation r = RoomDB.getInstance(HomeActivityNEW.this).reservationDao().getCurrentReservation();
                    if (r != null && r.isCheckedInNotCheckedOut()) {
                        CustomerServicesFragment customerServicesFragment = new CustomerServicesFragment();
                        transaction.replace(R.id.homeScreenContainer, customerServicesFragment);
                        transaction.commit();
                    } else {
                        CustomerServicesNoActiveReservationFragment customerServicesFragment = new CustomerServicesNoActiveReservationFragment();
                        transaction.replace(R.id.homeScreenContainer, customerServicesFragment);
                        transaction.commit();
                    }

                    break;
            }
            return true;
        }
    };

    DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
            if (fragmentToSet != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.homeScreenContainer, fragmentToSet)
                        .commit();
                fragmentToSet = null;
            }
        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new);
        drawerLayout = findViewById(R.id.homeDrawerLayout);
        bottomNavigationView = findViewById(R.id.bnvHomeScreen);
        navigationView = findViewById(R.id.homeScreenNavigationView);
        toolbar = findViewById(R.id.tbHomeScreen);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_png);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });

        drawerLayout.addDrawerListener(drawerListener);
        navigationView.setNavigationItemSelectedListener(navigationDrawerListener);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationListener);

        bottomNavigationView.setSelectedItemId(R.id.bottomNavigationRewardsProgram);
    }

    @Override
    public void pickedDate(int type, int day, int month, int year) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.homeScreenContainer);
        if (fragment instanceof ReservationNewFragment) {
            ReservationNewFragment reservationNewFragment = (ReservationNewFragment) fragment;
            reservationNewFragment.pickedDate(type, day, month, year);
        }
    }

    @Override
    public void newReservation() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.homeScreenContainer, ReservationNewFragment.newInstance())
                .commit();
    }

    public void myReservations() {
        List<Reservation> reservationList = RoomDB.getInstance(this).reservationDao().getAllUpcomingReservations();

        if (reservationList.isEmpty()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            UpcomingReservationNoneFragment fragment = UpcomingReservationNoneFragment.newInstance();
            transaction.replace(R.id.homeScreenContainer, fragment);
            transaction.commit();
        } else {
            List<MyReservationsAdapter.ReservationModel> reservationModelList = new ArrayList<>();
            for (Reservation r : reservationList) {
                try {
                    RoomType rt = RoomDB.getInstance(this).roomTypeDao().getRoomTypeById(r.getRoomTypeID());

                    SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    long currentTime = Calendar.getInstance().getTimeInMillis();
                    long startDate = sqlFormat.parse(r.getStartDate()).getTime();
                    long endDate = sqlFormat.parse(r.getEndDate()).getTime();

                    int reservationStatus;

                    if (!r.isCheckedIn())
                        if (currentTime < startDate)
                            reservationStatus = MyReservationsAdapter.ReservationModel.CANNOT_CHECK_IN;
                        else
                            reservationStatus = MyReservationsAdapter.ReservationModel.CAN_CHECK_IN;

                    else {
                        if (!r.isCheckedOut())
                            if (currentTime < endDate)
                                reservationStatus = MyReservationsAdapter.ReservationModel.CANNOT_CHECK_OUT;
                            else
                                reservationStatus = MyReservationsAdapter.ReservationModel.CAN_CHECK_OUT;
                        else
                            reservationStatus = MyReservationsAdapter.ReservationModel.IS_CHECKED_OUT;
                    }
                    reservationModelList.add(new MyReservationsAdapter.ReservationModel(r.getAdults(),
                            rt.getName(), r.getId(), r.getStartDate(), r.getEndDate(),
                            reservationStatus, r.getRoomNumber()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            UpcomingReservationRecyclerViewFragment fragment = UpcomingReservationRecyclerViewFragment.newInstance(reservationModelList);
            transaction.replace(R.id.homeScreenContainer, fragment);
            transaction.commit();
        }
    }
}
