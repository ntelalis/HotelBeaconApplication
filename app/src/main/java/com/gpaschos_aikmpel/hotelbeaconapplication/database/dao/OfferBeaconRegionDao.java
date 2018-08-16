package com.gpaschos_aikmpel.hotelbeaconapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.OfferBeaconRegion;

import java.util.List;

@Dao
public interface OfferBeaconRegionDao {

    @Query("SELECT * FROM OfferBeaconRegion")
    List<OfferBeaconRegion> getOfferBeaconRegion();

    @Query("SELECT OfferBeaconRegion.* FROM OfferBeaconRegion, BeaconRegion WHERE OfferBeaconRegion.regionID=BeaconRegion.id AND BeaconRegion.uniqueID=:uniqueID")
    List<OfferBeaconRegion> getOfferBeaconRegionByUniqueID(String uniqueID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OfferBeaconRegion offerBeaconRegion);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<OfferBeaconRegion> list);

    @Query("DELETE FROM OfferBeaconRegion WHERE OfferBeaconRegion.id=:id")
    void deleteOfferBeaconRegionByID(int id);

}