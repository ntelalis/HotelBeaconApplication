package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.adapters.ReservationsAdapter;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.ExclusiveOffer;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Loyalty;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomType;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.DatePickerFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.LoyaltyFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.MyRoomActiveFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.MyRoomInactiveFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.OfferExclusiveFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.OfferFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.CheckInFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.CheckedInFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.ReservationFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.UpcomingReservationNoneFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.UpcomingReservationRecyclerViewFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.LocalVariables;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.SyncServerData;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.Params;
import com.gpaschos_aikmpel.hotelbeaconapplication.notifications.NotificationCallbacks;
import com.gpaschos_aikmpel.hotelbeaconapplication.notifications.NotificationCreation;
import com.gpaschos_aikmpel.hotelbeaconapplication.notifications.ScheduleNotifications;
import com.gpaschos_aikmpel.hotelbeaconapplication.reworkRequest.RequestCallback;
import com.gpaschos_aikmpel.hotelbeaconapplication.utility.BottomNavigationViewHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements DatePickerFragment.DateSelected, RequestCallback, NotificationCallbacks, OfferExclusiveFragment.FragmentCallbacks, SyncServerData.CouponCallbacks, CheckedInFragment.Callbacks {

    public static final String TAG = HomeActivity.class.getSimpleName();

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    private Fragment fragmentToSet = null;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;

    private boolean needsRefresh = false;

    private NavigationView.OnNavigationItemSelectedListener navigationDrawerListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.navigationDrawerReservations:
                    intent = new Intent(HomeActivity.this, ReservationActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawers();
                    break;
                case R.id.navigationDrawerProfile:
                    drawerLayout.closeDrawers();
                    break;
                //TODO: don't clear the tables that are irrelevant to the customer

                case R.id.navigationDrawerLogout:
                    RoomDB.getInstance(HomeActivity.this).clearAllTables();
                    intent = new Intent(HomeActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.navigationDrawerContact:
                    intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + Params.TELEPHONE));
                    startActivity(intent);
                    break;
                case R.id.navigationDrawerHome:
                    drawerLayout.closeDrawers();
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
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(LoyaltyFragment.TAG);
                    if (fragment == null) {
                        LoyaltyFragment loyaltyFragment = new LoyaltyFragment();
                        transaction.replace(R.id.homeScreenContainer, loyaltyFragment, LoyaltyFragment.TAG);
                        transaction.commit();
                    }
                    break;
                case R.id.bottomNavigationOffers:
                    Fragment fragment1 = getSupportFragmentManager().findFragmentByTag(OfferFragment.TAG);
                    if (fragment1 == null) {
                        OfferFragment offerFragment = OfferFragment.newInstance();
                        transaction.replace(R.id.homeScreenContainer, offerFragment, OfferFragment.TAG);
                        transaction.commit();
                    }
                    break;
                case R.id.bottomNavigationMyRoom:
                    Reservation r = RoomDB.getInstance(HomeActivity.this).reservationDao().getCurrentReservation();
                    if (r != null && r.isCheckedInNotCheckedOut()) {
                        MyRoomActiveFragment myRoomActiveFragment = MyRoomActiveFragment.newInstance(r.getId(), r.getRoomNumber(), r.getRoomFloor());
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
        NavigationView navigationView = findViewById(R.id.appNavigationDrawer);
        Customer customer = RoomDB.getInstance(this).customerDao().getCustomer();
        Loyalty loyalty = RoomDB.getInstance(this).loyaltyDao().getLoyalty();
        View hView = navigationView.getHeaderView(0);
        TextView tvFName = hView.findViewById(R.id.tvNavigationDrawerFirstName);
        TextView tvLName = hView.findViewById(R.id.tvNavigationDrawerLastName);
        TextView tvAccNumber = hView.findViewById(R.id.tvNavigationDrawerAccountNumber);
        TextView tvTier = hView.findViewById(R.id.tvNavigationDrawerTier);
        tvFName.setText(customer.getFirstName());
        tvLName.setText(customer.getLastName());
        tvTier.setText(loyalty.getCurrentTierName());
        tvAccNumber.setText(String.valueOf(customer.getCustomerId()));
        Toolbar toolbar = findViewById(R.id.appToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(Params.HotelName);
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
        if (bundle != null) {
            Log.d("WTF", "WT22F");
            if (bundle.containsKey(NotificationCreation.CHECK_IN_NOTIFICATION) && bundle.containsKey(ScheduleNotifications.RESERVATION_ID)) {
                Log.d("WTF", "WaaTF");
                bottomNavigationView.setSelectedItemId(R.id.bottomNavigationReservations);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                int reservationID = bundle.getInt(ScheduleNotifications.RESERVATION_ID);
                CheckInFragment checkInFragment = CheckInFragment.newInstance(reservationID);
                Log.d("WTF", "" + reservationID);
                transaction.replace(R.id.homeScreenContainer, checkInFragment);
                transaction.commit();
            }
        }

        checkBeaconPermission();
    }

    private void checkBeaconPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //Permission is not granted. Should we show explanation?
                if (this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    //Show explanation if needed
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("This app needs location access");
                    builder.setMessage("Please grant location access so this app can detect beacons in order to be able to unlock your room door, receive special offers etc.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @RequiresApi(api = Build.VERSION_CODES.M)
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                        }
                    });
                    builder.show();
                } else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Coarse location permission granted");
                    LocalVariables.storeBoolean(this, R.string.beaconsEnabled, true);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    Log.d(TAG, "Coarse location permission denied and never asked again");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.show();
                    Log.d(TAG, "Coarse location permission denied");
                    LocalVariables.storeBoolean(this, R.string.beaconsEnabled, false);
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        needsRefresh = true;

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (needsRefresh) {
            bottomNavigationView.setSelectedItemId(bottomNavigationView.getSelectedItemId());
            needsRefresh = false;
        }
    }

    @Override
    public void pickedDate(int type, int day, int month, int year) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.homeScreenContainer);
        if (fragment instanceof ReservationFragment) {
            ReservationFragment reservationFragment = (ReservationFragment) fragment;
            reservationFragment.pickedDate(type, day, month, year);
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
            List<ReservationsAdapter.ReservationModel> reservationModelList = new ArrayList<>();
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
                            reservationStatus = ReservationsAdapter.ReservationModel.CANNOT_CHECK_IN;
                        else
                            reservationStatus = ReservationsAdapter.ReservationModel.CAN_CHECK_IN;

                    else {
                        if (!r.isCheckedOut())
                            if (currentTime < endDate)
                                reservationStatus = ReservationsAdapter.ReservationModel.CANNOT_CHECK_OUT;
                            else
                                reservationStatus = ReservationsAdapter.ReservationModel.CAN_CHECK_OUT;
                        else
                            reservationStatus = ReservationsAdapter.ReservationModel.IS_CHECKED_OUT;
                    }
                    reservationModelList.add(new ReservationsAdapter.ReservationModel(r.getAdults(),
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CheckInFragment fragment = CheckInFragment.newInstance(reservationID);
        transaction.replace(R.id.homeScreenContainer, fragment);
        transaction.commit();

    }

    @Override
    public void checkedIn(int reservationID) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CheckedInFragment fragment = CheckedInFragment.newInstance(reservationID);
        transaction.replace(R.id.homeScreenContainer, fragment);
        transaction.commit();
    }

    @Override
    public void getCoupon(int offerID) {
        SyncServerData.getInstance(this).getCoupon(offerID);
    }

    @Override
    public void couponCreated(ExclusiveOffer exclusiveOffer) {

        OfferFragment offerFragment = (OfferFragment) getSupportFragmentManager().findFragmentByTag(OfferFragment.TAG);
        if (offerFragment != null) {
            ViewPager viewPager = offerFragment.getViewpager();
            PagerAdapter pagerAdapter = viewPager.getAdapter();
            if (pagerAdapter != null) {
                OfferExclusiveFragment offerExclusiveFragment = (OfferExclusiveFragment) pagerAdapter.instantiateItem(viewPager, OfferFragment.OFFER_EXCLUSIVE);
                offerExclusiveFragment.refreshData();
            }
        }
    }

    @Override
    public void openMyRoom() {
        bottomNavigationView.setSelectedItemId(R.id.bottomNavigationMyRoom);
    }

    @Override
    public void success() {
        Toast.makeText(this, "Meow", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fail() {
        Toast.makeText(this, "Not Meow", Toast.LENGTH_SHORT).show();
    }
}
