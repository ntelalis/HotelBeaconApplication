package com.gpaschos_aikmpel.hotelbeaconapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypePointsAndCash;

import java.util.List;

@Dao
public interface RoomTypePointsAndCashDao {

    @Query("SELECT * FROM RoomTypePointsAndCash WHERE RoomTypeID=:roomTypeID AND Persons=:persons AND CurrencyID=:currencyID")
    RoomTypePointsAndCash getRoomTypePointsAndCash(int roomTypeID,int persons, int currencyID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RoomTypePointsAndCash> roomTypePointsAndCashList);
}