package com.gpaschos_aikmpel.hotelbeaconapplication.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.CustomerDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;

@Database(entities = {Customer.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB instance;

    public abstract CustomerDao customerDao();
}