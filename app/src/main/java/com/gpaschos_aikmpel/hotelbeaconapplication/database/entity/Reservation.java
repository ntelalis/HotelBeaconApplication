package com.gpaschos_aikmpel.hotelbeaconapplication.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

@Entity
public class Reservation {
    @PrimaryKey
    private int id;
    private int roomTypeID;
    private int adults;
    private String startDate;
    private String endDate;

    public Reservation(int id, int roomTypeID, int adults, String startDate, String endDate) {
        this.id = id;
        this.roomTypeID = roomTypeID;
        this.adults = adults;
        this.startDate = startDate;
        this.endDate = endDate;

    }

    public int getId() {
        return id;
    }


    public int getRoomTypeID() {
        return roomTypeID;
    }


    public int getAdults() {
        return adults;
    }


    public String getStartDate() {
        return startDate;
    }


    public String getEndDate() {
        return endDate;
    }
}
