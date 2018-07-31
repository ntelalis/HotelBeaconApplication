package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.BeaconRegion;
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
import org.altbeacon.beacon.service.RangedBeacon;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DoorUnlockActivity extends AppCompatActivity implements JsonListener, BeaconConsumer {

    private static final String TAG = DoorUnlockActivity.class.getSimpleName();

    private static final String roomBeaconUniqueID = "roomBeacon";

    private BluetoothAdapter bluetoothAdapter;
    private BeaconManager beaconManager;
    private Handler handler;
    private Runnable runnable;
    private FloatingActionButton fabDoorUnlock;

    private BroadcastReceiver bluetoothBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), BluetoothAdapter.ACTION_STATE_CHANGED)) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_ON:
                        fabDoorUnlock.setEnabled(true);
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        fabDoorUnlock.setEnabled(false);
                        Intent intent1 = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(intent1, 0);
                }
            }
        }
    };

    private boolean canOpenDoor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_unlock);

        //fabDoorUnlock = findViewById(R.id.fabDoorUnlock);

        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.bind(this);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        fabDoorUnlock = findViewById(R.id.fabDoorUnlock);
        if (bluetoothAdapter == null) {
            throw new RuntimeException("Cannot Find Bluetooth");
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(intent);
            }
        }
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(bluetoothBroadcastReceiver, filter);

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
        unregisterReceiver(bluetoothBroadcastReceiver);
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
    public void getSuccessResult(String url, JSONObject json) {
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
            String id ="1"; //= String.valueOf(r.getRoomBeaconId());
            final BeaconRegion beaconRegion = RoomDB.getInstance(this).beaconRegionDao().getRegionsByType("RoomDoor").get(0);
            Region region = new Region(beaconRegion.getUniqueID(), Identifier.parse(beaconRegion.getUUID()), Identifier.parse(beaconRegion.getMajor()), Identifier.parse(beaconRegion.getMinor()));
            beaconManager.addMonitorNotifier(new MonitorNotifier() {

                @Override
                public void didEnterRegion(Region region) {
                    if (region.getUniqueId().equals(beaconRegion.getUniqueID())) {
                        Log.d(TAG, "monitoring entered region" + region.getUniqueId());
                    }
                }

                @Override
                public void didExitRegion(Region region) {
                    if (region.getUniqueId().equals(beaconRegion.getUniqueID())) {
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
                    if (region.getUniqueId().equals(beaconRegion.getUniqueID())) {
                        if (collection.iterator().hasNext()) {
                            double distance = collection.iterator().next().getDistance();
                            Log.d(TAG, "range notifier" + distance);
                            if (distance < 0.5) {
                                canOpenDoor = true;
                                handler.removeCallbacks(runnable);
                                handler.postDelayed(runnable, 3000);
                            }
                        }
                    }
                }
            });
            RangedBeacon.setSampleExpirationMilliseconds(2000);
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
            Region region = new Region("roomBeacon", Identifier.parse(Params.beaconUUID), Identifier.parse("580"), Identifier.parse(""));

            try {
                beaconManager.startRangingBeaconsInRegion();
            }
        }*/

    }

}
