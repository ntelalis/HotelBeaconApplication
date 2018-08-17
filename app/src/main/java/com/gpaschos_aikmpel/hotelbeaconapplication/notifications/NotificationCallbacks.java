package com.gpaschos_aikmpel.hotelbeaconapplication.notifications;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;

public interface NotificationCallbacks {
    void checkIn(int reservationID);
    void checkedIn(int reservationID);
}
