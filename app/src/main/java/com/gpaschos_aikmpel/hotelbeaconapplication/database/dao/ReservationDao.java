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

    //@Query("SELECT * FROM Reservation r1 WHERE r1.StartDate = (SELECT MIN(r.StartDate) FROM Reservation r WHERE date(r.StartDate) >= date('now'))")
    //@Query("SELECT * FROM Reservation ORDER BY date(startDate) ASC")
    @Query("SELECT * FROM Reservation WHERE date(startDate)>date('now')")
    Reservation getUpcomingReservation();

    @Insert
    long insert(Reservation reservation);

    @Insert
    void insertAll(Reservation... reservations);

}
