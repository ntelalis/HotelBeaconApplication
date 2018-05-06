package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

import static com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.Params.beaconArea;

public class MonitoringActivity extends AppCompatActivity implements BeaconConsumer {

    private BeaconManager beaconManager;
    private TextView tvDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);
        tvDistance = findViewById(R.id.tvMonitoringDistance);
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {

        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MonitoringActivity.this, "First time seeing!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void didExitRegion(Region region) {
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MonitoringActivity.this, "No longer seeing!!!!", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void didDetermineStateForRegion(final int i, Region region) {
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MonitoringActivity.this, "I have just switched from seeing/not seeing beacons: " + i, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region(beaconArea, null, null, null));
        } catch (RemoteException e) {
        }

        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(final Collection<Beacon> collection, Region region) {
                if (collection.size() > 0) {
                    MonitoringActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("mew","meow2");
                            tvDistance.setText(String.valueOf(Math.floor(collection.iterator().next().getDistance() * 100) / 100));
                        }
                    });
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region(beaconArea, null, null, null));
        } catch (RemoteException e) {
        }
    }

}
