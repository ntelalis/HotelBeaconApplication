package com.gpaschos_aikmpel.hotelbeaconapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Loyalty;

@Dao
public interface LoyaltyDao {

    @Query("SELECT * FROM Loyalty LIMIT 1")
    Loyalty getLoyalty();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Loyalty loyalty);

    @Query("DELETE FROM Loyalty")
    void delete();
}
