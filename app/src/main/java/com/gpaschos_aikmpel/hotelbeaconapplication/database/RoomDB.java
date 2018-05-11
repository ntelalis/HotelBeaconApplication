package com.gpaschos_aikmpel.hotelbeaconapplication.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.CustomerDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.ReservationDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.DateConverter;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;

@Database(entities = {Customer.class, Reservation.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB instance;

    public abstract CustomerDao customerDao();

    public abstract ReservationDao reservationDao();
}