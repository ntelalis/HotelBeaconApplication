package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

public class CustomerServicesNoActiveReservationFragment extends Fragment {

    public CustomerServicesNoActiveReservationFragment() {

    }

    public static CustomerServicesNoActiveReservationFragment newInstance() {
        CustomerServicesNoActiveReservationFragment fragment = new CustomerServicesNoActiveReservationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_services_no_active_reservation, container, false);
    }

}
