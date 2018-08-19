package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation;

public interface ReservationCallbacks {
    void book(int roomTypeID, String arrival, String departure, int adults, int children);

    void showReservations();

    void showBooked(int reservationID);
}
