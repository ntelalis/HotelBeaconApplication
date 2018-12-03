package com.gpaschos_aikmpel.hotelbeaconapplication.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

@Entity(primaryKeys = {"roomTypeID", "adults", "children"},
        foreignKeys = {@ForeignKey(entity = RoomType.class,
                parentColumns = "id",
                childColumns = "roomTypeID")})
public class RoomTypeCashPoints {

    private int roomTypeID;
    private int adults;
    private int children;
    private int cash;
    private int points;

    public RoomTypeCashPoints(int roomTypeID, int adults, int children, int cash, int points) {
        this.roomTypeID = roomTypeID;
        this.adults = adults;
        this.children = children;
        this.cash = cash;
        this.points = points;
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

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
