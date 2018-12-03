package com.gpaschos_aikmpel.hotelbeaconapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypeCash;

import java.util.List;

@Dao
public interface RoomTypeCashDao {

    @Query("SELECT * FROM RoomTypeCash WHERE RoomTypeID=:roomTypeID AND Adults=:adults AND Children=:children")
    RoomTypeCash getRoomTypeCash(int roomTypeID, int adults, int children);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RoomTypeCash> roomTypeCashList);
}
