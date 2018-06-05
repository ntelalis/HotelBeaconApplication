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
import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.RoomTypeFreeNightsPointsDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.RoomTypePointsAndCashDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Beacon;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Country;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Currency;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomType;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypeCash;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypeFreeNightsPoints;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypePointsAndCash;

@Database(entities = {Customer.class, Reservation.class, Beacon.class, RoomType.class, Country.class, RoomTypeCash.class, RoomTypeFreeNightsPoints.class, RoomTypePointsAndCash.class, Currency.class}, version = 1, exportSchema = false)
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

    public abstract RoomTypeFreeNightsPointsDao roomTypeFreeNightsPointsDao();

    public abstract RoomTypePointsAndCashDao roomTypePointsAndCashDao();

    public abstract CurrencyDao currencyDao();
}