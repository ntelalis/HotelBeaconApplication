package com.gpaschos_aikmpel.hotelbeaconapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Country;
import com.gpaschos_aikmpel.hotelbeaconapplication.utility.CountAndAverage;

import java.util.List;

@Dao
public interface CountryDao {

    @Query("SELECT * FROM Country")
    List<Country> getCountries();

    @Query("SELECT COUNT(ID) || ',' || MAX(Modified) FROM Country")
    String getModified();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Country> countryList);

}
