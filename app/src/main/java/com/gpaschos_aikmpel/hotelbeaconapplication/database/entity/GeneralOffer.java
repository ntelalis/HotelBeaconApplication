package com.gpaschos_aikmpel.hotelbeaconapplication.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.gpaschos_aikmpel.hotelbeaconapplication.utility.SyncModel;

@Entity
public class GeneralOffer implements SyncModel {

    @PrimaryKey
    private int id;
    private String price;
    private String discount;
    private String description;
    private String startDate;
    private String endDate;
    private String modified;

    public GeneralOffer(int id, String price, String discount, String description, String startDate, String endDate, String modified) {
        this.id = id;
        this.price = price;
        this.discount = discount;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.modified = modified;
    }



    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
