package com.gpaschos_aikmpel.hotelbeaconapplication.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.gpaschos_aikmpel.hotelbeaconapplication.utility.SyncModel;

@Entity
public class ExclusiveOffer implements SyncModel {

    @PrimaryKey
    private int id;
    private int serviceID;
    private String priceDiscount;
    private String description;
    private String title;
    private String details;
    private boolean special;
    private String startDate;
    private String endDate;
    private String code;
    private boolean codeUsed;
    private String codeCreated;
    private String modified;


    public void updateCoupon(String code, boolean codeUsed, String codeCreated) {
        this.code = code;
        this.codeUsed = codeUsed;
        this.codeCreated = codeCreated;
    }

    public ExclusiveOffer(int id, int serviceID, String priceDiscount, String description, String title, String details, boolean special, String startDate, String endDate, String code, boolean codeUsed, String codeCreated, String modified) {
        this.id = id;
        this.serviceID = serviceID;
        this.priceDiscount = priceDiscount;
        this.description = description;
        this.title = title;
        this.details = details;
        this.special = special;
        this.startDate = startDate;
        this.endDate = endDate;
        this.code = code;
        this.codeUsed = codeUsed;
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

    public String getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(String priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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

    public boolean isCodeUsed() {
        return codeUsed;
    }

    public void setCodeUsed(boolean codeUsed) {
        this.codeUsed = codeUsed;
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