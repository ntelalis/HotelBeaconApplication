package com.gpaschos_aikmpel.hotelbeaconapplication.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class RoomType {

    @PrimaryKey
    private int id;
    private String name;
    private int capacity;
    private int adults;
    private boolean childrenSupported;
    private String image;
    private String description;
    private String modified;

    public RoomType(int id, String name, int capacity, int adults, boolean childrenSupported, String image, String description, String modified) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.adults = adults;
        this.childrenSupported = childrenSupported;
        this.image = image;
        this.description = description;
        this.modified = modified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public boolean isChildrenSupported() {
        return childrenSupported;
    }

    public void setChildrenSupported(boolean childrenSupported) {
        this.childrenSupported = childrenSupported;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
