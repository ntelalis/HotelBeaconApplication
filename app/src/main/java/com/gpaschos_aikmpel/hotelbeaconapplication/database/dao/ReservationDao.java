package com.gpaschos_aikmpel.hotelbeaconapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;

import java.util.List;

@Dao
public interface ReservationDao {

    @Query("SELECT * FROM Reservation")
    List<Reservation> getAllReservations();

    @Query("SELECT * FROM Reservation r1 WHERE r1.StartDate = (SELECT MIN(r.StartDate) FROM Reservation r WHERE r.StartDate >= date('now'))")
    Reservation getUpcomingReservation();

    @Insert
    void insert(Reservation reservation);

    @Insert
    void insertAll(Reservation... reservations);

}
