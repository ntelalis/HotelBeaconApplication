package com.gpaschos_aikmpel.hotelbeaconapplication.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.BeaconDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.CountryDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.CurrencyDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.CustomerDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.ReservationDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.RoomTypeCashDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.RoomTypeDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.RoomTypePointsDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.RoomTypeCashPointsDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.TitleDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Beacon;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Country;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Currency;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomType;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypeCash;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypePoints;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypeCashPoints;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Title;

@Database(entities = {Customer.class, Reservation.class, Beacon.class, RoomType.class, Country.class, RoomTypeCash.class, RoomTypePoints.class, RoomTypeCashPoints.class, Currency.class, Title.class}, version = 1, exportSchema = false)
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

    public abstract CountryDao countryDao();

    public abstract RoomTypeCashDao roomTypeCashDao();

    public abstract RoomTypePointsDao roomTypePointsDao();

    public abstract RoomTypeCashPointsDao roomTypeCashPointsDao();

    public abstract CurrencyDao currencyDao();

    public abstract TitleDao titleDao();
}