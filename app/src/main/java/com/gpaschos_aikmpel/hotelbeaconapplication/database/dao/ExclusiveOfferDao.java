package com.gpaschos_aikmpel.hotelbeaconapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.ExclusiveOffer;

import java.util.List;

@Dao
public interface ExclusiveOfferDao {

    @Query("SELECT * FROM ExclusiveOffer")
    List<ExclusiveOffer> getExclusiveOffers();

    @Query("SELECT * FROM ExclusiveOffer WHERE ExclusiveOffer.id=:id")
    ExclusiveOffer getOfferByID(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ExclusiveOffer offer);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ExclusiveOffer> list);

    @Query("DELETE FROM ExclusiveOffer WHERE ExclusiveOffer.id=:id")
    void deleteExclusiveOfferByID(int id);

}