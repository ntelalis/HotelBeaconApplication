package com.gpaschos_aikmpel.hotelbeaconapplication.utility;

import android.arch.persistence.room.PrimaryKey;

public class IDValue {
    @PrimaryKey
    int id;
    String value;

    public IDValue(int id, String value) {
        this.id = id;
        this.value= value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
