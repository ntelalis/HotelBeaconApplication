package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.util.Log;

import com.gpaschos_aikmpel.hotelbeaconapplication.NotificationsFunctions.NotificationCreation;
import com.gpaschos_aikmpel.hotelbeaconapplication.activities.CheckedInActivity;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.Params;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
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


    private RegionBootstrap regionBootstrap;
    private BackgroundPowerSaver backgroundPowerSaver;
    private BeaconManager beaconManager;

    public RoomDB database;

    @Override
    public void onCreate() {
        super.onCreate();

        //BeaconManager and BackgroundPowerSaver init
        beaconManager = BeaconManager.getInstanceForApplication(this);
        backgroundPowerSaver = new BackgroundPowerSaver(this);

        //Scanning Settings
        //beaconManager.setBackgroundBetweenScanPeriod((long)150000);
        beaconManager.setBackgroundBetweenScanPeriod((long) 15000);
        beaconManager.setBackgroundScanPeriod((long) 1100);

        //Region scanning setup
        Region region = new Region("welcomeBeacon", Identifier.parse(Params.beaconArea), null, null);

        regionBootstrap = new RegionBootstrap(this, region);
    }

    public void checkin(int reservationID){
        Map<String, String> params = new HashMap<>();
        params.put(POST.checkinReservationID, String.valueOf(reservationID));
        VolleyQueue.getInstance(this).jsonRequest(this, URL.checkinUrl, params);

    }

    @Override
    public void didEnterRegion(Region region) {
        Log.d("BeaconApplication", "Region: " + region.getUniqueId() + " found");
        if (region.getUniqueId().equals("welcomeBeacon")) {
            NotificationCreation.notifyWelcome(this);
        }
    }

    @Override
    public void didExitRegion(Region region) {

    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        if(url.equals(URL.checkinUrl)) {
            String room = json.getString(POST.checkinRoom);
            Intent intent = new Intent(this, CheckedInActivity.class);
            intent.putExtra(CheckedInActivity.ROOM, room);
            startActivity(intent);

            
        }
    }

    @Override
    public void getErrorResult(String url, String error) {

    }
}
