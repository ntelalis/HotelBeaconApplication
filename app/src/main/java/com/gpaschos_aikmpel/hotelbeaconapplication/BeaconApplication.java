package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.app.Application;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.activities.CheckedInActivity;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.UpcomingReservationNoneFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.Params;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.notifications.NotificationCreation;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BeaconApplication extends Application implements BootstrapNotifier, JsonListener {

    private static final String TAG = BeaconApplication.class.getSimpleName();

    private RegionBootstrap regionBootstrap;
    private BackgroundPowerSaver backgroundPowerSaver;
    private BeaconManager beaconManager;

    public RoomDB database;


    @Override
    public void onCreate() {
        super.onCreate();

        //Create notification channel
        NotificationCreation.channel(this, "basic_channel", "default channel");

        //BeaconManager and BackgroundPowerSaver init
        beaconManager = BeaconManager.getInstanceForApplication(this);
        backgroundPowerSaver = new BackgroundPowerSaver(this);

        //Scanning Settings
        beaconManager.setBackgroundBetweenScanPeriod((long) 150000);
        //beaconManager.setBackgroundBetweenScanPeriod((long) 15000);
        beaconManager.setBackgroundScanPeriod((long) 1100);

        //Region scanning setup
        Region region = new Region("welcomeBeacon", Identifier.parse(Params.beaconUUID), null, null);
        Region restaurant = new Region("restaurantBeacon", Identifier.parse(Params.beaconUUID), null, null);


        //ADD NEW REGIONS HERE
        //Region region1 = new Region("roomBeacon",Identifier.parse(Params.beaconUUID),null,null);


        regionBootstrap = new RegionBootstrap(this, region);
        //regionBootstrap.addRegion(region1);

    }

    public void checkin(int reservationID) {
        Map<String, String> params = new HashMap<>();
        params.put(POST.checkInReservationID, String.valueOf(reservationID));
        VolleyQueue.getInstance(this).jsonRequest(this, URL.checkInUrl, params);

    }

    @Override
    public void didEnterRegion(Region region) {
        Log.d(TAG, "Entered Region: " + region.getUniqueId());

        if (region.getUniqueId().equals("welcomeBeacon")) {
            NotificationCreation.notifyWelcome(this);
            NotificationCreation.notifyFarewell(this);
        }

        if (region.getUniqueId().equals("restaurantBeacon")) {
            NotificationCreation.notifyOffers(this,region.getUniqueId());
        }
    }

    @Override
    public void didExitRegion(Region region) {
        if (region.getUniqueId().equals("welcomeBeacon")) {
            Log.d(TAG, "Exited from Region" + region.getUniqueId());
        }
    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

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

            Intent intent = new Intent(this, CheckedInActivity.class);
            intent.putExtra(CheckedInActivity.ROOM, roomNumber);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, url + "" + error, Toast.LENGTH_SHORT).show();
    }
}
