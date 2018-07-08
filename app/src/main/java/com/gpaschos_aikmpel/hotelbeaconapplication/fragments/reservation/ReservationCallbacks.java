package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation;

public interface ReservationCallbacks {
    void newReservation();
    void book(int roomTypeID, String arrival, String departure, int adults, int children);
}
