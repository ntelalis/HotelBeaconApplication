package com.gpaschos_aikmpel.hotelbeaconapplication.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Loyalty {

    @PrimaryKey
    private int customerID;
    private int currentPoints;
    private String currentTierName;
    private int currentTierPoints;
    private String nextTierName;
    private int nextTierPoints;
    private String modified;

    public Loyalty(int customerID, int currentPoints, String currentTierName, int currentTierPoints, String nextTierName, int nextTierPoints, String modified) {
        this.customerID = customerID;
        this.currentPoints = currentPoints;
        this.currentTierName = currentTierName;
        this.currentTierPoints = currentTierPoints;
        this.nextTierName = nextTierName;
        this.nextTierPoints = nextTierPoints;
        this.modified = modified;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(int currentPoints) {
        this.currentPoints = currentPoints;
    }

    public String getCurrentTierName() {
        return currentTierName;
    }

    public void setCurrentTierName(String currentTierName) {
        this.currentTierName = currentTierName;
    }

    public int getCurrentTierPoints() {
        return currentTierPoints;
    }

    public void setCurrentTierPoints(int currentTierPoints) {
        this.currentTierPoints = currentTierPoints;
    }

    public String getNextTierName() {
        return nextTierName;
    }

    public void setNextTierName(String nextTierName) {
        this.nextTierName = nextTierName;
    }

    public int getNextTierPoints() {
        return nextTierPoints;
    }

    public void setNextTierPoints(int nextTierPoints) {
        this.nextTierPoints = nextTierPoints;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}

