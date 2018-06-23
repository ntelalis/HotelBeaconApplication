package com.gpaschos_aikmpel.hotelbeaconapplication.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(primaryKeys = {"roomTypeID", "adults", "children"},
        foreignKeys = {@ForeignKey(entity = RoomType.class,
                parentColumns = "id",
                childColumns = "roomTypeID")})
public class RoomTypePoints {
    private int roomTypeID;
    private int adults;
    private int children;
    private int spendingPoints;
    private int gainingPoints;

    public RoomTypePoints(int roomTypeID, int adults, int children, int spendingPoints, int gainingPoints) {
        this.roomTypeID = roomTypeID;
        this.adults = adults;
        this.children = children;
        this.spendingPoints = spendingPoints;
        this.gainingPoints = gainingPoints;
    }

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public int getSpendingPoints() {
        return spendingPoints;
    }

    public void setSpendingPoints(int spendingPoints) {
        this.spendingPoints = spendingPoints;
    }

    public int getGainingPoints() {
        return gainingPoints;
    }

    public void setGainingPoints(int gainingPoints) {
        this.gainingPoints = gainingPoints;
    }
}
