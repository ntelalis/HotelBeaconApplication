package com.gpaschos_aikmpel.hotelbeaconapplication.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Beacon {

    @PrimaryKey
    private int id;
    private String UUID;
    private String major;
    private String minor;

    public Beacon(int id, String UUID, String major, String minor) {
        this.id = id;
        this.UUID = UUID;
        this.major = major;
        this.minor = minor;
    }

    public int getId() {
        return id;
    }

    public String getUUID() {
        return UUID;
    }

    public String getMajor() {
        return major;
    }

    public String getMinor() {
        return minor;
    }
}
