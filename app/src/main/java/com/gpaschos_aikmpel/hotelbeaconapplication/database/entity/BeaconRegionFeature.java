package com.gpaschos_aikmpel.hotelbeaconapplication.database.entity;

import com.gpaschos_aikmpel.hotelbeaconapplication.utility.SyncModel;

public class BeaconRegionFeature implements SyncModel{

    private int id;
    private int regionID;
    private String beaconRegionType;
    private String modified;

    public BeaconRegionFeature(int id, int regionID, String beaconRegionType, String modified) {
        this.id = id;
        this.regionID = regionID;
        this.beaconRegionType = beaconRegionType;
        this.modified = modified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRegionID() {
        return regionID;
    }

    public void setRegionID(int regionID) {
        this.regionID = regionID;
    }

    public String getBeaconRegionType() {
        return beaconRegionType;
    }

    public void setBeaconRegionType(String beaconRegionType) {
        this.beaconRegionType = beaconRegionType;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
