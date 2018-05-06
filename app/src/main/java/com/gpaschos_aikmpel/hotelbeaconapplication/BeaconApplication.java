package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.app.Application;
import android.content.Intent;

import com.gpaschos_aikmpel.hotelbeaconapplication.activities.HomeActivity;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.Params;

import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import static com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.Params.beaconArea;

public class BeaconApplication extends Application implements BootstrapNotifier {


    private RegionBootstrap regionBootstrap;

    @Override
    public void onCreate() {
        super.onCreate();
        Region region = new Region("com.gpaschos_aikmpel.hotelbeaconapplication", Identifier.parse(beaconArea), null, null);
        regionBootstrap = new RegionBootstrap(this, region);

    }

    @Override
    public void didEnterRegion(Region region) {
        if(true){
        //if (region.getId1().equals(Params.beaconArea)) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void didExitRegion(Region region) {

    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

    }
}
