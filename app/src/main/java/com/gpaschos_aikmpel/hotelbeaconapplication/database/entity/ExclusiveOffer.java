package com.gpaschos_aikmpel.hotelbeaconapplication.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.gpaschos_aikmpel.hotelbeaconapplication.utility.SyncModel;

@Entity
public class ExclusiveOffer implements SyncModel {

    @PrimaryKey
    private int id;
    private int serviceID;
    private String price;
    private String discount;
    private String description;
    private boolean special;
    private String startDate;
    private String endDate;
    private String code;
    private boolean claimed;
    private String codeCreated;
    private String modified;

    public ExclusiveOffer(int id, int serviceID, String price, String discount, String description, boolean special, String startDate, String endDate, String code, boolean claimed, String codeCreated, String modified) {
        this.id = id;
        this.serviceID = serviceID;
        this.price = price;
        this.discount = discount;
        this.description = description;
        this.special = special;
        this.startDate = startDate;
        this.endDate = endDate;
        this.code = code;
        this.claimed = claimed;
        this.codeCreated = codeCreated;
        this.modified = modified;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }

    public String getCodeCreated() {
        return codeCreated;
    }

    public void setCodeCreated(String codeCreated) {
        this.codeCreated = codeCreated;
    }

    @Override
    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}