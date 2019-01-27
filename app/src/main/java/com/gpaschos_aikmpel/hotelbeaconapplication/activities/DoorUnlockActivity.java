package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.BeaconRegion;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.LocalVariables;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.Params;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
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
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    private BeaconManager beaconManager;
    private Handler handler;
    private Runnable runnable;
    private FloatingActionButton fabDoorUnlock;

    private Reservation reservation;

    private boolean canOpenDoor;
    private boolean canUseBeacons;

    //Our door region
    private Region region;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_door_unlock);

        reservation = RoomDB.getInstance(this).reservationDao().getCurrentReservation();

        TextView tvRoom = findViewById(R.id.tvDoorUnlockRoom);
        tvRoom.setText(String.valueOf(reservation.getRoomNumber()));

        TextView tvTouchToUnlock = findViewById(R.id.tvDoorUnlockPrompt);

        final int black = 0xff000000;
        final int black_transparent = 0x00000000;

        ValueAnimator colorAnim = ObjectAnimator.ofInt(tvTouchToUnlock, "textColor", black, black_transparent);
        colorAnim.setDuration(3000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();

        TextView tvFloor = findViewById(R.id.tvDoorUnlockFloor);
        tvFloor.setText(String.valueOf(reservation.getRoomFloor()));

        canUseBeacons = LocalVariables.readBoolean(this, R.string.beaconsEnabled, true);

        beaconManager = BeaconManager.getInstanceForApplication(this);
        //Also detect iBeacons
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));

        beaconManager.bind(this);

        fabDoorUnlock = findViewById(R.id.fabDoorUnlock);

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

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

        //If Marshmallow and above we need extra permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkBeaconPermission();
        }

        //Get the BeaconRegion for this room door beacon
        BeaconRegion beaconRegion = RoomDB.getInstance(this).beaconRegionFeatureDao().getRegionByFeature(Params.DOOR_UNLOCK);
        //Create the new region in order to track it
        region = new Region(beaconRegion.getUniqueID(), Identifier.parse(beaconRegion.getUUID()), Identifier.parse(beaconRegion.getMajor()), Identifier.parse(beaconRegion.getMinor()));
    }

    //Check locationEnabled
    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkBeaconPermission() {
        if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("You are not able to use this feature. Reset app preferences if you changed your mind");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.show();
                    Log.d(TAG, "Coarse location permission denied and never asked again");
                    canUseBeacons = false;
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, you are not able to use this feature.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.show();
                    Log.d(TAG, "Coarse location permission denied");
                    canUseBeacons = false;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //We cannot open the door until door beacon is found
        canOpenDoor = false;

        //Create a new handler which run the runnable below to disable button after X time
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                //Disable button
                canOpenDoor = false;
                changeButtonColor(R.color.buttonDeactivated);
            }
        };

        //Check if Location is enabled
        if (!isLocationEnabled(getApplicationContext())) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Services");
            builder.setMessage("You need to enable location services in order for this feature to work");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @RequiresApi(api = Build.VERSION_CODES.M)
                public void onDismiss(DialogInterface dialog) {
                    Intent loc = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(loc);
                }
            });
            builder.show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.removeAllRangeNotifiers();
        //Unregister beaconmanager
        if (beaconManager != null) {
            beaconManager.unbind(this);
        }
        //Remove Bluetooth broadcast receiver
        unregisterReceiver(bluetoothBroadcastReceiver);
    }


    //Send unlock command to server
    public void unlockDoor(View view) {
        if (canUseBeacons) {
            int reservationId = reservation.getId();
            String roomPass = reservation.getRoomPassword();
            Log.d(TAG, reservationId + " " + roomPass);

            Map<String, String> params = new HashMap<>();
            params.put(POST.doorUnlockReservationID, String.valueOf(reservationId));
            params.put(POST.doorUnlockRoomPassword, roomPass);

            if (canOpenDoor) {
                VolleyQueue.getInstance(this).jsonRequest(this, URL.doorUnlockUrl, params);
            } else {
                Snackbar.make(fabDoorUnlock, "Please get in 1 meter range from your door and wait for the button to change color", Snackbar.LENGTH_LONG).show();
            }
        } else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.fabDoorUnlock), "Feature Disabled", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) {
        //Door was unlocked
        Snackbar.make(fabDoorUnlock, "Unlocked", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void getErrorResult(String url, String error) {
        //Door unlock was denied
        Snackbar.make(fabDoorUnlock, "There is a problem with your key. Please contact our reception", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onBeaconServiceConnect() {
        Log.d(TAG,"Beacon connect");

        //register a rangeNotifier to see how far are we from the door
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

                //Since we have only one region we don't need to detect which region was found

                //Check if any beacon is detected for our region filter
                Log.d(TAG, beacons.size() + " Beacons found for " + region.getUniqueId());
                if (beacons.size() > 0) {
                    //Get the first and only beacon from our collection
                    Beacon beacon = beacons.iterator().next();
                    double distance = beacon.getDistance();
                    Log.d(TAG, "Door is " + (double) Math.round(distance * 100) / 100 + " meters away.");
                    if (distance < 1.3) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                canOpenDoor = true;
                                changeButtonColor(R.color.buttonActivated);
                            }
                        });

                        //Reset disable button runnable
                        handler.removeCallbacks(runnable);
                        handler.postDelayed(runnable, 6200);
                    }
                }
            }
        });

        try {
            //Set how many ms we want our detections to last for calculating distance
            RangedBeacon.setSampleExpirationMilliseconds(4200);
            //Set how much to wait between scans
            beaconManager.setForegroundBetweenScanPeriod(0);
            //Set the scanning period
            beaconManager.setForegroundScanPeriod(2000);
            //Start ranging for our region
            beaconManager.startRangingBeaconsInRegion(region);
            Log.d(TAG, "Ranging started");
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.e(TAG, "Ranging failed");
        }
    }

    //Helper function to change button color
    private void changeButtonColor(int color) {
        fabDoorUnlock.setBackgroundTintList(ContextCompat.getColorStateList(this, color));
    }
}
