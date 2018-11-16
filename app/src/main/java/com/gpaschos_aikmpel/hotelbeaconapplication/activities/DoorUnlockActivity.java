package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.Manifest;
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
import android.widget.Toast;

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
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

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
    private boolean canUseBeacons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_door_unlock);

        Reservation r = RoomDB.getInstance(this).reservationDao().getCurrentReservation();

        TextView tvRoom = findViewById(R.id.tvDoorUnlockRoom);
        tvRoom.setText(String.valueOf(r.getRoomNumber()));

        TextView tvFloor = findViewById(R.id.tvDoorUnlockFloor);
        tvFloor.setText(String.valueOf(r.getRoomFloor()));

        canUseBeacons = LocalVariables.readBoolean(this, R.string.beaconsEnabled, true);

        beaconManager = BeaconManager.getInstanceForApplication(this);
        //Also detect iBeacons
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.bind(this);
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkBeaconPermission();
        } else {
            initActivity();
        }
    }

    private void initActivity() {


    }

    //for demonstration purposes
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
                    initActivity();
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
        canOpenDoor = false;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                canOpenDoor = false;
                changeButtonColor(R.color.colorPrimary);
            }
        };


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
        if (beaconManager != null) {
            beaconManager.unbind(this);
            unregisterReceiver(bluetoothBroadcastReceiver);
        }
    }


    public void unlockDoor(View view) {
        Reservation r = RoomDB.getInstance(this).reservationDao().getCurrentReservation();
        if (canUseBeacons) {
            if (r != null && r.isCheckedInNotCheckedOut()) {
                int reservationId = r.getId();
                String roomPass = r.getRoomPassword();
                Log.d(TAG, reservationId + " " + roomPass);

                Map<String, String> params = new HashMap<>();
                params.put(POST.doorUnlockReservationID, String.valueOf(reservationId));
                params.put(POST.doorUnlockRoomPassword, roomPass);

                if (canOpenDoor) {
                    //Snackbar.make(fabDoorUnlock,"Opening door. Please wait",Snackbar.LENGTH_LONG).show();
                    //Toast.makeText(this, "opening door....", Toast.LENGTH_SHORT).show();
                    VolleyQueue.getInstance(this).jsonRequest(this, URL.doorUnlockUrl, params);
                } else {
                    Snackbar.make(fabDoorUnlock, "Please get in 1 meter range from your door and wait for the button to become orange", Snackbar.LENGTH_LONG).show();
                    //Toast.makeText(this, "Please get in 1 meter range from your door and wait for the button to become orange", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "No active reservation found!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.fabDoorUnlock), "Feature Disabled", Snackbar.LENGTH_LONG);
            snackbar.show();

        }
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) {
        Snackbar.make(fabDoorUnlock, "Unlocked", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void getErrorResult(String url, String error) {

        Snackbar.make(fabDoorUnlock, "There is a problem with your key. Please contact our reception", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onBeaconServiceConnect() {

        Reservation r = RoomDB.getInstance(this).reservationDao().getCurrentReservation();

        Log.d(TAG, "check if reservation");
        if (r != null && r.isCheckedInNotCheckedOut()) {
            Log.d(TAG, "reservation found");
            final BeaconRegion beaconRegion = RoomDB.getInstance(this).beaconRegionFeatureDao().getRegionByFeature(Params.DOOR_UNLOCK);
            Log.d(TAG, "Doorunlock Region from Room " + beaconRegion.getUniqueID() + " " + beaconRegion.getUUID() + " " + beaconRegion.getMajor() + " " + beaconRegion.getMinor());
            Region region = new Region(beaconRegion.getUniqueID(), Identifier.parse(beaconRegion.getUUID()), Identifier.parse(beaconRegion.getMajor()), Identifier.parse(beaconRegion.getMinor()));

            beaconManager.addRangeNotifier(new RangeNotifier() {
                @Override
                public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
                    if (region.getUniqueId().equals(beaconRegion.getUniqueID())) {
                        Log.d(TAG, "Size of detected Beacons: " + collection.size());
                        if (collection.iterator().hasNext()) {
                            double distance = collection.iterator().next().getDistance();
                            Log.d(TAG, "range notifier" + distance);
                            if (distance < 1.3) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        canOpenDoor = true;
                                        changeButtonColor(R.color.colorAccent);
                                    }
                                });
                                handler.removeCallbacks(runnable);
                                Log.i(TAG,"in");
                                handler.postDelayed(runnable, 6200);
                            }
                            else{
                                Log.i(TAG,"out");
                            }

                        }
                    }
                }
            });

            //RangedBeacon.setMaxTrackinAge(1000);
            //BeaconManager.setRegionExitPeriod(1100);
            //TODO Uncomment this?

            try {
                RangedBeacon.setSampleExpirationMilliseconds(4200);
                beaconManager.startRangingBeaconsInRegion(region);
                beaconManager.setForegroundBetweenScanPeriod(0);
                beaconManager.setForegroundScanPeriod(2000);
                beaconManager.startRangingBeaconsInRegion(region);
                Log.d(TAG, "ranging started");
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.e(TAG, "ranging failed");
            }

        }
    }

    private void changeButtonColor(int color) {
        fabDoorUnlock.setBackgroundTintList(ContextCompat.getColorStateList(this, color));
    }
}
