package com.gpaschos_aikmpel.hotelbeaconapplication.notifications;

public interface NotificationCallbacks {
    void checkIn(int reservationID);

    void checkedIn(int reservationID);
}
