package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.app.Application;
import android.util.Log;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.BeaconRegion;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.BeaconRegionFeature;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.ExclusiveOffer;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.OfferBeaconRegion;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.LocalVariables;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.SyncServerData;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.Params;
import com.gpaschos_aikmpel.hotelbeaconapplication.notifications.NotificationCreation;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.service.ArmaRssiFilter;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BeaconApplication extends Application implements BootstrapNotifier, SyncServerData.CouponCallbacks {

    private static final String TAG = BeaconApplication.class.getSimpleName();

    private boolean beaconsEnabled;
    private RegionBootstrap regionBootstrap;
    private BackgroundPowerSaver backgroundPowerSaver;
    private BeaconManager beaconManager;
    private Map<String, List<BeaconRegionFeature>> featureListMap;

    public RoomDB database;


    @Override
    public void onCreate() {
        super.onCreate();

        beaconsEnabled = LocalVariables.readBoolean(this, R.string.beaconsEnabled, true);

        //Create notification channel
        NotificationCreation.channel(this, "basic_channel", "default channel");

        if (beaconsEnabled) {
            //BeaconManager and BackgroundPowerSaver init
            beaconManager = BeaconManager.getInstanceForApplication(this);
            //BeaconManager.setRssiFilterImplClass(ArmaRssiFilter.class);
            //ArmaRssiFilter.setDEFAULT_ARMA_SPEED(0.1);
            backgroundPowerSaver = new BackgroundPowerSaver(this);

            //Scanning Settings
            //beaconManager.setBackgroundBetweenScanPeriod((long) 150000);
            //beaconManager.setBackgroundBetweenScanPeriod((long) 15000);
            beaconManager.setBackgroundBetweenScanPeriod((long) 2000);
            beaconManager.setBackgroundScanPeriod((long) 1500);

            //Also detect iBeacons
            beaconManager.getBeaconParsers().add(new BeaconParser()
                    .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        }

    }


    public void registerBeaconRegion() {
        if (beaconsEnabled) {
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

            getRegionFeature(beaconRegionList);

            regionBootstrap = new RegionBootstrap(this, regionList);
        }
    }

    //update the hashmap with the features of each region within the background scanning regions list
    private void getRegionFeature(List<BeaconRegion> beaconRegionList) {
        Log.d(TAG, "In getRegionFeature: getting featureList");
        featureListMap = new HashMap<>();
        for (BeaconRegion beaconRegion : beaconRegionList) {
            List<BeaconRegionFeature> regionFeatureList = RoomDB.getInstance(this).beaconRegionFeatureDao().getFeatureByUniqueID(beaconRegion.getUniqueID());
            featureListMap.put(beaconRegion.getUniqueID(), regionFeatureList);
        }
    }

    @Override
    public void didEnterRegion(Region region) {
        List<BeaconRegionFeature> featureList = featureListMap.get(region.getUniqueId());

        //FIXME WHAT CRASHES THINGS HERE??
        //FIXME BEACON DETECTION AFTER APP IS STARTED WTF
        //FIXME DOOR BEACON DOESNT GET DETECT FIRST TIME
        for (BeaconRegionFeature feature : featureList) {
            switch (feature.getFeature()) {
                case Params.WELCOME:
                    NotificationCreation.notifyWelcome(this);
                    break;
                case Params.FAREWELL:
                    NotificationCreation.notifyFarewell(this);
                    break;
                case Params.OFFER:
                    selectOffer(region.getUniqueId());
                    break;
            }
        }
    }

    private void selectOffer(String regionUniqueID) {
        List<OfferBeaconRegion> offerBeaconRegionList = RoomDB.getInstance(this).offerBeaconRegionDao().getOfferBeaconRegionByUniqueID(regionUniqueID);
        List<ExclusiveOffer> specialOfferList = new ArrayList<>();
        List<ExclusiveOffer> exclusiveOfferList = new ArrayList<>();
        //DEMO Code Start
        List<ExclusiveOffer> alreadySentOfferList = new ArrayList<>();
        //DEMO Code End
        ExclusiveOffer selectedExclusiveOffer = null;

        for (OfferBeaconRegion offerBeaconRegion : offerBeaconRegionList) {
            ExclusiveOffer exclusiveOffer = RoomDB.getInstance(this).exclusiveOfferDao().getOfferByID(offerBeaconRegion.getOfferID());
            if (exclusiveOffer.isSpecial() && exclusiveOffer.getCode() == null) {
                specialOfferList.add(exclusiveOffer);
                continue;
            }
            //DEMO Code Start
            if (exclusiveOffer.isSpecial() && exclusiveOffer.getCode() != null) {
                alreadySentOfferList.add(exclusiveOffer);
            }
            //DEMO Code End
            if (exclusiveOffer.getCode() == null) {
                exclusiveOfferList.add(exclusiveOffer);
            }
        }

        if (specialOfferList.size() > 0) {
            selectedExclusiveOffer = specialOfferList.get(new Random().nextInt(specialOfferList.size()));
        } else if (exclusiveOfferList.size() > 0) {
            selectedExclusiveOffer = exclusiveOfferList.get(new Random().nextInt(exclusiveOfferList.size()));
        }
        //DEMO Code Start
        else if (alreadySentOfferList.size() > 0) {
            NotificationCreation.notifyOffer(this, alreadySentOfferList.get(new Random().nextInt(alreadySentOfferList.size())));
        }
        //DEMO Code End

        if (selectedExclusiveOffer != null) {
            SyncServerData.getInstance(this).getCoupon(selectedExclusiveOffer.getId());

        }
    }

    @Override
    public void didExitRegion(Region region) {
    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

    }

    @Override
    public void couponCreated(ExclusiveOffer exclusiveOffer) {
        NotificationCreation.notifyOffer(this, exclusiveOffer);
    }
}
