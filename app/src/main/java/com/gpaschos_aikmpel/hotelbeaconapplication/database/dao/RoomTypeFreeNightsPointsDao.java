package com.gpaschos_aikmpel.hotelbeaconapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypeCash;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypeFreeNightsPoints;

import java.util.List;

@Dao
public interface RoomTypeFreeNightsPointsDao {

    @Query("SELECT * FROM RoomTypeFreeNightsPoints WHERE RoomTypeID=:roomTypeID AND Persons=:persons")
    RoomTypeFreeNightsPoints getRoomTypeFreeNightsPoints(int roomTypeID, int persons);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RoomTypeFreeNightsPoints> roomTypeFreeNightsPointsList);
}
