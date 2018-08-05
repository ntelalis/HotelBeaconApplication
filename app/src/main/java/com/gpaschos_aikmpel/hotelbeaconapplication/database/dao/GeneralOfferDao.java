package com.gpaschos_aikmpel.hotelbeaconapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.GeneralOffer;

import java.util.List;

@Dao
public interface GeneralOfferDao {

    @Query("SELECT * FROM GeneralOffer")
    List<GeneralOffer> getGeneralOffers();

    @Query("SELECT * FROM GeneralOffer WHERE GeneralOffer.id=:id")
    GeneralOffer getOfferByID(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GeneralOffer offer);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<GeneralOffer> list);

    @Query("DELETE FROM GeneralOffer WHERE GeneralOffer.id=:id")
    void deleteGeneralOfferByID(int id);
}
