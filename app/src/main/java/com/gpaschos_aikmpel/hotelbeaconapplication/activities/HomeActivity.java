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
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.MyRoomActiveFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.MyRoomInactiveFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.DatePickerFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.LoyaltyFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.OfferFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.CheckInFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.CheckedInFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.ReservationNewFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.UpcomingReservationNoneFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.UpcomingReservationRecyclerViewFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.notifications.NotificationCallbacks;
import com.gpaschos_aikmpel.hotelbeaconapplication.notifications.NotificationCreation;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;
import com.gpaschos_aikmpel.hotelbeaconapplication.utility.BottomNavigationViewHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements DatePickerFragment.DateSelected, NotificationCallbacks, JsonListener {

    private Fragment fragmentToSet = null;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private NavigationView.OnNavigationItemSelectedListener navigationDrawerListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.navigationDrawerReservations:
                    intent = new Intent(HomeActivity.this, NewReservationActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawers();
                    break;
                case R.id.navigationDrawerProfile:
                    break;
                case R.id.navigationDrawerLogout:
                    RoomDB.getInstance(HomeActivity.this).clearAllTables();
                    intent = new Intent(HomeActivity.this, LoginActivity.class);
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
                case R.id.bottomNavigationOffers:
                    OfferFragment offerFragment = OfferFragment.newInstance();
                    transaction.replace(R.id.homeScreenContainer, offerFragment);
                    transaction.commit();
                    break;
                case R.id.bottomNavigationMyRoom:
                    Reservation r = RoomDB.getInstance(HomeActivity.this).reservationDao().getCurrentReservation();
                    if (r != null && r.isCheckedInNotCheckedOut()) {
                        MyRoomActiveFragment myRoomActiveFragment = MyRoomActiveFragment.newInstance(r.getId(),r.getRoomNumber(),r.getRoomFloor());
                        transaction.replace(R.id.homeScreenContainer, myRoomActiveFragment);
                        transaction.commit();
                    } else {
                        MyRoomInactiveFragment customerServicesFragment = new MyRoomInactiveFragment();
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
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.homeDrawerLayout);
        bottomNavigationView = findViewById(R.id.bnvHomeScreen);
        navigationView = findViewById(R.id.appNavigationDrawer);
        toolbar = findViewById(R.id.appToolbar);
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
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            if(bundle.containsKey(NotificationCreation.CHECK_IN_NOTIFICATION)){
                bottomNavigationView.setSelectedItemId(R.id.bottomNavigationReservations);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                CheckInFragment checkInFragment = CheckInFragment.newInstance();
                transaction.replace(R.id.homeScreenContainer, checkInFragment);
                transaction.commit();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(bottomNavigationView.getSelectedItemId());
    }

    @Override
    public void pickedDate(int type, int day, int month, int year) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.homeScreenContainer);
        if (fragment instanceof ReservationNewFragment) {
            ReservationNewFragment reservationNewFragment = (ReservationNewFragment) fragment;
            reservationNewFragment.pickedDate(type, day, month, year);
        }
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

    @Override
    public void checkIn(int reservationID) {
        Map<String, String> params = new HashMap<>();
        params.put(POST.checkInReservationID, String.valueOf(reservationID));
        VolleyQueue.getInstance(this).jsonRequest(this, URL.checkInUrl, params);
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        if (url.equals(URL.checkInUrl)) {
            int reservationID = json.getInt(POST.checkInReservationID);
            int roomNumber = json.getInt(POST.checkInRoomNumber);
            String checkInDate = json.getString(POST.checkInDate);
            int roomFloor = json.getInt(POST.checkInRoomFloor);
            int beaconRegionID = json.getInt(POST.checkInBeaconRegionID);
            String roomPassword = json.getString(POST.checkInRoomPassword);
            String modified = json.getString(POST.checkInModified);
            //update Room with the checked-in information
            Reservation r = RoomDB.getInstance(this).reservationDao().getReservationByID(reservationID);
            r.checkIn(checkInDate, roomNumber, roomFloor, roomPassword, beaconRegionID, modified);
            RoomDB.getInstance(this).reservationDao().update(r);

            /*Intent intent = new Intent(this, CheckedInActivity.class);
            intent.putExtra(CheckedInActivity.ROOM, roomNumber);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            */

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            CheckedInFragment checkedInFragment = CheckedInFragment.newInstance(reservationID);
            transaction.replace(R.id.homeScreenContainer, checkedInFragment);
            transaction.commit();
        }
    }

    @Override
    public void getErrorResult(String url, String error) {

    }
}
