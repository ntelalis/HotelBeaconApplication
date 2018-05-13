package com.gpaschos_aikmpel.hotelbeaconapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomType;

import java.util.List;

@Dao
public interface RoomTypeDao {

    @Query("SELECT * FROM RoomType")
    List<RoomType> getRoomTypes();

    @Query("SELECT * FROM RoomType WHERE id = :id")
    RoomType getRoomTypeById(int id);

    @Query("SELECT * FROM RoomType")
    List<RoomType> check();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RoomType> roomTypeList);
}
