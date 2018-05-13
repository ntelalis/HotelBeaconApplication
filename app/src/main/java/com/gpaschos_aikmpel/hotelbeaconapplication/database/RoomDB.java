package com.gpaschos_aikmpel.hotelbeaconapplication.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.BeaconDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.CustomerDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.ReservationDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.RoomTypeDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Beacon;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomType;

@Database(entities = {Customer.class, Reservation.class, Beacon.class, RoomType.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    private static final String DB_NAME = "HotelDatabase";
    private static RoomDB dbInstance;

    public static RoomDB getInstance(Context context) {
        if (dbInstance == null) {
            //FIXME dont use allowMainThreadQueries
            dbInstance = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DB_NAME).allowMainThreadQueries().build();
        }
        return dbInstance;
    }

    public abstract CustomerDao customerDao();

    public abstract ReservationDao reservationDao();

    public abstract BeaconDao beaconDao();

    public abstract RoomTypeDao roomTypeDao();
}