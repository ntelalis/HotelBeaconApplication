package com.gpaschos_aikmpel.hotelbeaconapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Beacon;

@Dao
public interface BeaconDao {

    @Query("SELECT * FROM Beacon Where Beacon.id = :id")
    Beacon getBeacon(int id);

    @Insert
    void insert(Beacon beacon);

}
