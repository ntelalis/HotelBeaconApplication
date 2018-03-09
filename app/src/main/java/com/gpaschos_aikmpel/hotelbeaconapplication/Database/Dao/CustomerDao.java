package com.gpaschos_aikmpel.hotelbeaconapplication.Database.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gpaschos_aikmpel.hotelbeaconapplication.Database.Entity.Customer;

import java.util.List;

@Dao
public interface CustomerDao {

    @Query("SELECT * FROM Customer LIMIT 1")
    Customer getAll();

}
