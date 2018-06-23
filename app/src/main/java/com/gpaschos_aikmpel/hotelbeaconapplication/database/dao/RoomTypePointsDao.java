package com.gpaschos_aikmpel.hotelbeaconapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypePoints;

import java.util.List;

@Dao
public interface RoomTypePointsDao {

    @Query("SELECT * FROM RoomTypePoints WHERE RoomTypeID=:roomTypeID AND Adults=:adults")
    RoomTypePoints getRoomTypePoints(int roomTypeID, int adults);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RoomTypePoints> roomTypeFreeNightsPointsList);
}
