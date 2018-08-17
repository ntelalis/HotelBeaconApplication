package com.gpaschos_aikmpel.hotelbeaconapplication.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Loyalty {

    @PrimaryKey
    private int customerID;
    private String currentTier;
    private String nextTier;
    private int currentPoints;
    private int currentTierPoints;
    private int nextTierPoints;

    public Loyalty(int customerID, String currentTier, String nextTier, int currentPoints, int currentTierPoints, int nextTierPoints) {
        this.customerID = customerID;
        this.currentTier = currentTier;
        this.nextTier = nextTier;
        this.currentPoints = currentPoints;
        this.currentTierPoints = currentTierPoints;
        this.nextTierPoints = nextTierPoints;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCurrentTier() {
        return currentTier;
    }

    public void setCurrentTier(String currentTier) {
        this.currentTier = currentTier;
    }

    public String getNextTier() {
        return nextTier;
    }

    public void setNextTier(String nextTier) {
        this.nextTier = nextTier;
    }

    public int getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(int currentPoints) {
        this.currentPoints = currentPoints;
    }

    public int getCurrentTierPoints() {
        return currentTierPoints;
    }

    public void setCurrentTierPoints(int currentTierPoints) {
        this.currentTierPoints = currentTierPoints;
    }

    public int getNextTierPoints() {
        return nextTierPoints;
    }

    public void setNextTierPoints(int nextTierPoints) {
        this.nextTierPoints = nextTierPoints;
    }
}

