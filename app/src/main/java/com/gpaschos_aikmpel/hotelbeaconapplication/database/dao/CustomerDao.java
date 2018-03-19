package com.gpaschos_aikmpel.hotelbeaconapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;

@Dao
public interface CustomerDao {

    @Query("SELECT * FROM Customer LIMIT 1")
    Customer getCustomer();

    @Insert
    void insert(Customer customer);

}
