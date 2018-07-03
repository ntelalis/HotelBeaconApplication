package com.gpaschos_aikmpel.hotelbeaconapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.BeaconRegion;

import java.util.List;

@Dao
public interface BeaconRegionDao {

    @Query("SELECT * FROM BeaconRegion Where BeaconRegion.id = :id")
    BeaconRegion getBeaconRegion(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BeaconRegion beaconRegion);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<BeaconRegion> beaconRegionList);

}
