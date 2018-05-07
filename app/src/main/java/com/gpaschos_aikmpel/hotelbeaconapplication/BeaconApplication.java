package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.app.Application;
import android.util.Log;

import com.gpaschos_aikmpel.hotelbeaconapplication.NotificationsFunctions.NotificationWelcome;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.Params;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

public class BeaconApplication extends Application implements BootstrapNotifier {


    private RegionBootstrap regionBootstrap;
    private BackgroundPowerSaver backgroundPowerSaver;
    private BeaconManager beaconManager;

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

    @Override
    public void didEnterRegion(Region region) {
        Log.d("BeaconApplication", "Region: " + region.getUniqueId() + " found");
        if (region.getUniqueId().equals("welcomeBeacon")) {
            NotificationWelcome.getInstance(this).notifyWelcome();
        }
    }

    @Override
    public void didExitRegion(Region region) {

    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

    }

}
