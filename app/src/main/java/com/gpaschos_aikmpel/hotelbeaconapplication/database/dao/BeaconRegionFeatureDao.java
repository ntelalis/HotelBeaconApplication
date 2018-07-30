package com.gpaschos_aikmpel.hotelbeaconapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.BeaconRegionFeature;

import java.util.List;

@Dao
public interface BeaconRegionFeatureDao {

    @Query("SELECT * FROM BeaconRegionFeature")
    List<BeaconRegionFeature> getRegionFeatureList();

    @Query("SELECT * FROM BeaconRegionFeature WHERE beaconregionfeature.regionID = :regionID")
    List<BeaconRegionFeature> getRegionFeatureListByRegionID(int regionID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BeaconRegionFeature region);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<BeaconRegionFeature> list);

    @Query("DELETE FROM BeaconRegionFeature WHERE BeaconRegionFeature.id=:id")
    void deleteRegionFeatureByID(int id);

}
