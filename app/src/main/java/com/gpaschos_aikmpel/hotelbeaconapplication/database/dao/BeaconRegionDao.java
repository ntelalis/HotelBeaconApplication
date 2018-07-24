package com.gpaschos_aikmpel.hotelbeaconapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.BeaconRegion;

import java.util.List;

@Dao
public interface BeaconRegionDao {

    @Query("SELECT * FROM BeaconRegion")
    List<BeaconRegion> getRegions();

    @Query("SELECT * FROM BeaconRegion WHERE BeaconRegion.background=1")
    List<BeaconRegion> getBackgroundScanningRegions();

    @Query("SELECT * FROM BeaconRegion WHERE BeaconRegion.id=:id")
    BeaconRegion getRegionByID(int id);

    @Query("SELECT * FROM BeaconRegion WHERE BeaconRegion.regionType=:type")
    List<BeaconRegion> getRegionByType(String type);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BeaconRegion region);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<BeaconRegion> list);

    @Query("DELETE FROM BeaconRegion WHERE BeaconRegion.id=:id")
    void deleteRegionByID(int id);
}
