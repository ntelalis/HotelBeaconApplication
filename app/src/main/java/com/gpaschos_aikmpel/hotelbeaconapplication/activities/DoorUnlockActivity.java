package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.Params;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DoorUnlockActivity extends AppCompatActivity implements JsonListener, BeaconConsumer {

    private static final String TAG = DoorUnlockActivity.class.getSimpleName();

    private static final String roomBeaconUniqueID = "roomBeacon";

    private BeaconManager beaconManager;
    private Handler handler;
    private Runnable runnable;
    private Button btnDoorUnlock;

    private boolean canOpenDoor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_unlock);

        //btnDoorUnlock = findViewById(R.id.btnDoorUnlock);

        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        canOpenDoor = false;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                canOpenDoor = false;
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    public void unlockDoor(View view) {
        Reservation r = RoomDB.getInstance(this).reservationDao().getCurrentReservation();
        if (r != null && r.isCheckedInNotCheckedOut()) {
            int reservationId = r.getId();
            String roomPass = r.getRoomPassword();
            Log.d(TAG, reservationId + " " + roomPass);

            Map<String, String> params = new HashMap<>();
            params.put(POST.doorUnlockReservationID, String.valueOf(reservationId));
            params.put(POST.doorUnlockRoomPassword, roomPass);

            if (canOpenDoor) {
                VolleyQueue.getInstance(this).jsonRequest(this, URL.doorUnlockUrl, params);
            } else {
                Toast.makeText(this, "Please get in 1 meter range from your door", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "No active reservation found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        Toast.makeText(this, "Unlocked!", Toast.LENGTH_SHORT).show();
        //beaconManager.removeAllMonitorNotifiers();
        //beaconManager.removeAllRangeNotifiers();
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, "Wrong Password!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBeaconServiceConnect() {

        Reservation r = RoomDB.getInstance(this).reservationDao().getCurrentReservation();

        Log.d(TAG, "check if reservation");
        if (r != null && r.isCheckedInNotCheckedOut()) {
            Log.d(TAG, "reservation found");
            String id = String.valueOf(r.getRoomBeaconId());
            Region region = new Region(roomBeaconUniqueID, Identifier.parse(Params.beaconArea), Identifier.parse("580"), Identifier.parse(id));
            beaconManager.addMonitorNotifier(new MonitorNotifier() {

                @Override
                public void didEnterRegion(Region region) {
                    Log.d(TAG, "monitoring entered region" + region.getUniqueId());
                    if (region.getUniqueId().equals(roomBeaconUniqueID)) {

                    }
                }

                @Override
                public void didExitRegion(Region region) {
                    if (region.getUniqueId().equals(roomBeaconUniqueID)) {
                        Log.d(TAG, "exited region" + region.getUniqueId());
                    }
                }

                @Override
                public void didDetermineStateForRegion(int i, Region region) {

                }
            });

            try {
                beaconManager.startMonitoringBeaconsInRegion(region);
                Log.d(TAG, "monitoring started");
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.e(TAG, "monitoring failed");
            }

            beaconManager.addRangeNotifier(new RangeNotifier() {
                @Override
                public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
                    Log.d(TAG, "range before region notifier");
                    if (region.getUniqueId().equals(roomBeaconUniqueID)) {
                        if (collection.iterator().hasNext()) {
                            double distance = collection.iterator().next().getDistance();
                            Log.d(TAG, "range notifier" + distance);
                            if (distance < (double) 1) {
                                canOpenDoor = true;
                                handler.removeCallbacks(runnable);
                                handler.postDelayed(runnable, 3000);
                            }
                        }
                    }
                }
            });
            try {
                beaconManager.startRangingBeaconsInRegion(region);
                Log.d(TAG, "ranging started");
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.e(TAG, "ranging failed");
            }

        }

        //TODO implement this in database
        /*Reservation r = RoomDB.getInstance(this).reservationDao().getCurrentReservation();
        if (r != null && r.isCheckedInNotCheckedOut()) {
            int id =r.getRoomBeaconId();
            com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Beacon beacon = RoomDB.getInstance(this).beaconDao().getBeacon(id);
            Region region = new Region(roomBeaconUniqueID,Identifier.parse(beacon.getUUID()),Identifier.parse(beacon.getMajor()),Identifier.parse(beacon.getMinor()));
            Region region = new Region("roomBeacon", Identifier.parse(Params.beaconArea), Identifier.parse("580"), Identifier.parse(""));

            try {
                beaconManager.startRangingBeaconsInRegion();
            }
        }*/

    }

}