package com.gpaschos_aikmpel.hotelbeaconapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Country;

import java.util.List;

@Dao
public interface CountryDao {

    @Query("SELECT * FROM Country")
    List<Country> getCountries();

    @Query("SELECT * FROM Country WHERE id=:id")
    Country getCountryByID(int id);

    @Insert
    void insertAll(List<Country> countryList);

}
