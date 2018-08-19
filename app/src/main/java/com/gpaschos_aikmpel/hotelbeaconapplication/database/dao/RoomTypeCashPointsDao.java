package com.gpaschos_aikmpel.hotelbeaconapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypeCashPoints;

import java.util.List;

@Dao
public interface RoomTypeCashPointsDao {

    @Query("SELECT * FROM RoomTypeCashPoints WHERE RoomTypeID=:roomTypeID AND Adults=:adults AND CurrencyID=:currencyID")
    RoomTypeCashPoints getRoomTypeCashPoints(int roomTypeID, int adults, int currencyID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RoomTypeCashPoints> roomTypePointsAndCashList);
}