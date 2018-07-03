package com.gpaschos_aikmpel.hotelbeaconapplication.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class BeaconRegion {

    @PrimaryKey
    private int id;
    private String uniqueID;
    private String UUID;
    private String major;
    private String minor;

    public BeaconRegion(int id, String uniqueID, String UUID, String major, String minor) {
        this.id = id;
        this.uniqueID = uniqueID;
        this.UUID = UUID;
        this.major = major;
        this.minor = minor;
    }

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
}
