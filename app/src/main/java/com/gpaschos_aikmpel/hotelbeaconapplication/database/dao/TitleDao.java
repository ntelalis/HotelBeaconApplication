package com.gpaschos_aikmpel.hotelbeaconapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Title;

import java.util.List;

@Dao
public interface TitleDao {

    @Query("SELECT * FROM Title")
    List<Title> getTitles();

    @Query("SELECT * FROM Title WHERE id=:id")
    Title getTitleByID(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Title> titleList);
}
