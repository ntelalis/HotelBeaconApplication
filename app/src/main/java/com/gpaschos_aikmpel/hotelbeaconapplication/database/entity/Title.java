package com.gpaschos_aikmpel.hotelbeaconapplication.database.entity;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.gpaschos_aikmpel.hotelbeaconapplication.utility.IDValue;

@Entity
public class Title extends IDValue {

    public Title(@NonNull int id, String value) {
        super(id,value);
    }
}
