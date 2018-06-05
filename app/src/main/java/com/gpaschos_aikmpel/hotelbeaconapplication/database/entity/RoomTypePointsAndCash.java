package com.gpaschos_aikmpel.hotelbeaconapplication.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

@Entity(primaryKeys = {"roomTypeID", "persons", "currencyID"},
        indices = {@Index(value = "currencyID")},
        foreignKeys = {@ForeignKey(entity = RoomType.class,
                parentColumns = "id",
                childColumns = "roomTypeID"),
                @ForeignKey(entity = Currency.class,
                        parentColumns = "id",
                        childColumns = "currencyID")})
public class RoomTypePointsAndCash {

    private int roomTypeID;
    private int persons;
    private int currencyID;
    private int cash;
    private int points;

    public RoomTypePointsAndCash(int roomTypeID, int persons, int currencyID, int cash, int points) {
        this.roomTypeID = roomTypeID;
        this.persons = persons;
        this.currencyID = currencyID;
        this.cash = cash;
        this.points = points;
    }

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public int getPersons() {
        return persons;
    }

    public void setPersons(int persons) {
        this.persons = persons;
    }

    public int getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(int currencyID) {
        this.currencyID = currencyID;
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
