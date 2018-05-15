package com.gpaschos_aikmpel.hotelbeaconapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;

import java.util.List;

@Dao
public interface ReservationDao {


    @Query("SELECT * FROM Reservation r1 WHERE r1.StartDate = (SELECT MIN(r.StartDate) FROM Reservation r WHERE r.StartDate >= date('now','localtime'))")
    Reservation getUpcomingReservation();

    @Query("SELECT * FROM Reservation r WHERE r.startDate >= date('now','localtime')")
    List<Reservation> getAllUpcomingReservations();

    @Query("SELECT * FROM Reservation r1 WHERE r1.StartDate <= date('now','localtime') AND r1.endDate >= date('now','localtime') AND r1.checkIn IS NOT NULL AND  r1.checkOut IS NULL")
    Reservation getCurrentReservation();

    @Query("SELECT * FROM Reservation r1 WHERE r1.StartDate <= date('now','localtime') AND r1.endDate >= date('now','localtime') AND r1.checkIn IS NULL")
    Reservation getCurrentNotCheckedInReservation();

    @Query("SELECT * FROM Reservation r1 WHERE r1.StartDate <= date('now','localtime') AND r1.endDate >= date('now','localtime') AND r1.checkIn IS NULL AND r1.checkOut IS NOT NULL")
    Reservation getCurrentCheckedInNotCheckedOutReservation();
    

    @Query("SELECT * FROM Reservation r1 WHERE r1.id=:id")
    Reservation getReservationByID(int id);

    @Query("SELECT * FROM Reservation r1 WHERE r1.StartDate >= :startDate AND r1.endDate <= :endDate")
    Reservation getReservationWithinDate(String startDate, String endDate);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Reservation reservation);

    @Update
    void update(Reservation reservation);

}
