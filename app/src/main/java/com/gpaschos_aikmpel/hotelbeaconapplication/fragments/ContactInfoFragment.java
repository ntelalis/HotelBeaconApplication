package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;

public class ContactInfoFragment extends Fragment {

    private Customer customer;

    public ContactInfoFragment() {
        // Required empty public constructor
    }

    public static ContactInfoFragment newInstance() {
        ContactInfoFragment fragment = new ContactInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customer = RoomDB.getInstance(getContext()).customerDao().getCustomer();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_info, container, false);

        if (customer != null) {
            TextInputEditText tietPhone = view.findViewById(R.id.tietContactPhone);
            tietPhone.setText(customer.getPhone());
            TextInputEditText tietAddress1 = view.findViewById(R.id.tietContactAddress1);
            tietAddress1.setText(customer.getAddress1());
            TextInputEditText tietAddress2 = view.findViewById(R.id.tietContactAddress2);
            tietAddress2.setText(customer.getAddress2());
            TextInputEditText tietCity = view.findViewById(R.id.tietContactCity);
            tietCity.setText(customer.getCity());
            TextInputEditText tietPostalCode = view.findViewById(R.id.tietContactPostal);
            tietPostalCode.setText(customer.getPostalCode());
        }

        return view;
    }

}
