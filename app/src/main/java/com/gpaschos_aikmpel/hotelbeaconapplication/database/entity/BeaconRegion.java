package com.gpaschos_aikmpel.hotelbeaconapplication.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.gpaschos_aikmpel.hotelbeaconapplication.utility.SyncModel;

@Entity
public class BeaconRegion implements SyncModel {

    @PrimaryKey
    private int id;
    private String uniqueID;
    private String UUID;
    private String major;
    private String minor;
    private boolean exclusive;
    private boolean background;
    private String regionType;
    private String modified;

    public BeaconRegion(int id, String uniqueID, String UUID, String major, String minor, boolean exclusive, boolean background, String regionType, String modified) {
        this.id = id;
        this.uniqueID = uniqueID;
        this.UUID = UUID;
        this.major = major;
        this.minor = minor;
        this.exclusive = exclusive;
        this.background = background;
        this.regionType = regionType;
        this.modified = modified;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public boolean isExclusive() {
        return exclusive;
    }

    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
    }

    public boolean isBackground() {
        return background;
    }

    public void setBackground(boolean background) {
        this.background = background;
    }

    public String getRegionType() {
        return regionType;
    }

    public void setRegionType(String regionType) {
        this.regionType = regionType;
    }

    @Override
    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
