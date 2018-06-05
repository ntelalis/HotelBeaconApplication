package com.gpaschos_aikmpel.hotelbeaconapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Currency;

import java.util.List;

@Dao
public interface CurrencyDao {

    @Query("SELECT * FROM Currency")
    List<Currency> getCurrencies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Currency> currencyList);
}
