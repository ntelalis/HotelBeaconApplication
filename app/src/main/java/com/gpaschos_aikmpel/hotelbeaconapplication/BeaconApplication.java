package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.activities.CheckedInActivity;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.BeaconRegion;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.BeaconRegionFeature;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.JSONHelper;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.notifications.NotificationCreation;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeaconApplication extends Application implements BootstrapNotifier, JsonListener {

    private static final String TAG = BeaconApplication.class.getSimpleName();

    private RegionBootstrap regionBootstrap;
    private BackgroundPowerSaver backgroundPowerSaver;
    private BeaconManager beaconManager;
    private Map<String, List<BeaconRegionFeature>> featureListMap;

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
        //beaconManager.setBackgroundBetweenScanPeriod((long) 150000);
        beaconManager.setBackgroundBetweenScanPeriod((long) 15000);
        beaconManager.setBackgroundScanPeriod((long) 1100);

        beaconManager.getBeaconParsers().add(new BeaconParser().
                        setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));



        //Region scanning setup
        //Region region = new Region("welcomeBeacon", Identifier.parse(Params.beaconUUID), null, null);
        //Region restaurant = new Region("restaurantBeacon", Identifier.parse(Params.beaconUUID), null, null);


        //ADD NEW REGIONS HERE
        //Region region1 = new Region("roomBeacon",Identifier.parse(Params.beaconUUID),null,null);


        //regionBootstrap.addRegion(region1);

    }

    public void checkin(int reservationID) {
        Toast.makeText(this, "" + reservationID, Toast.LENGTH_SHORT).show();
        Map<String, String> params = new HashMap<>();
        params.put(POST.checkInReservationID, String.valueOf(reservationID));
        VolleyQueue.getInstance(this).jsonRequest(this, URL.checkInUrl, params);
        //FIXME On deleting occupancy syncing has wrong info for checkin. later modified date than server

    }

    public void registerBeaconRegion() {
        //regionList is synced, proceeding with regions creation

        List<BeaconRegion> beaconRegionList = RoomDB.getInstance(this).beaconRegionDao().getBackgroundScanningRegions();
        List<Region> regionList = new ArrayList<>();
        for (BeaconRegion beaconRegion : beaconRegionList) {
            String uuidString = beaconRegion.getUUID();
            Identifier uuid = null;
            if (uuidString != null) {
                uuid = Identifier.parse(uuidString);
            }

            String majorString = beaconRegion.getMajor();
            Identifier major = null;
            if (majorString != null) {
                major = Identifier.parse(majorString);

            }
            String minorString = beaconRegion.getMinor();
            Identifier minor = null;
            if (minorString != null) {
                minor = Identifier.parse(minorString);
            }

            Region r = new Region(beaconRegion.getUniqueID(), uuid, major, minor);
            regionList.add(r);
            Log.d(TAG, "region: " + beaconRegion.getUniqueID() + " " + uuidString + " " + majorString + " " + minorString);
        }

        regionBootstrap = new RegionBootstrap(this, regionList);

        getRegionFeature(beaconRegionList);
    }

    //update the hashmap with the features of each region within the background scanning regions list
    private void getRegionFeature(List<BeaconRegion> beaconRegionList) {
        featureListMap = new HashMap<>();
        for (BeaconRegion beaconRegion : beaconRegionList) {
            List<BeaconRegionFeature> regionFeatureList = RoomDB.getInstance(this).beaconRegionFeatureDao().getFeatureByUniqueID(beaconRegion.getUniqueID());
            featureListMap.put(beaconRegion.getUniqueID(), regionFeatureList);
        }
    }

    @Override
    public void didEnterRegion(Region region) {
        Log.d(TAG, "Entered Region: " + region.getUniqueId());
        List<BeaconRegionFeature> featureList = featureListMap.get(region.getUniqueId());
        for (BeaconRegionFeature feature : featureList) {
            switch (feature.getRegionType()) {
                case "welcome":
                    NotificationCreation.notifyWelcome(this);
                    break;
                case "farewell":
                    NotificationCreation.notifyFarewell(this);
                    break;
                case "offer":
                    //offersystem
                    break;
            }
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
            String roomPassword = json.getString(POST.checkInRoomPassword);
            String modified = json.getString(POST.checkInModified);
            //update Room with the checked-in information
            JSONArray roomRegionArray = json.getJSONArray(POST.checkInRoomBeaconRegionArray);
            List<BeaconRegion> roomRegions = new ArrayList<>();
            for (int i = 0; i < roomRegionArray.length(); i++) {
                JSONObject roomRegionObject = roomRegionArray.getJSONObject(i);
                int roomBeaconRegionID = roomRegionObject.getInt(POST.checkInRoomBeaconRegionID);
                String roomBeaconRegionUniqueID = JSONHelper.getString(roomRegionObject, POST.checkInRoomBeaconRegionUniqueID);
                String roomBeaconRegionUUID = JSONHelper.getString(roomRegionObject, POST.checkInRoomBeaconRegionUUID);
                String roomBeaconRegionMajor = JSONHelper.getString(roomRegionObject, POST.checkInRoomBeaconRegionMajor);
                String roomBeaconRegionMinor = JSONHelper.getString(roomRegionObject, POST.checkInRoomBeaconRegionMinor);
                boolean roomBeaconRegionExclusive = roomRegionObject.getBoolean(POST.checkInRoomBeaconRegionExclusive);
                boolean roomBeaconRegionBackground = roomRegionObject.getBoolean(POST.checkInRoomBeaconRegionBackground);
                String roomBeaconRegionModified = JSONHelper.getString(roomRegionObject, POST.checkInRoomBeaconRegionModified);

                roomRegions.add(new BeaconRegion(roomBeaconRegionID, roomBeaconRegionUniqueID, roomBeaconRegionUUID,
                        roomBeaconRegionMajor, roomBeaconRegionMinor, roomBeaconRegionExclusive, roomBeaconRegionBackground,
                        "room", roomBeaconRegionModified));
            }

            Reservation r = RoomDB.getInstance(this).reservationDao().getReservationByID(reservationID);
            r.checkIn(checkInDate, roomNumber, roomFloor, roomPassword, modified);
            RoomDB.getInstance(this).reservationDao().update(r);

            RoomDB.getInstance(this).beaconRegionDao().insertAll(roomRegions);

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
