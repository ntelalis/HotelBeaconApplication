package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.utility.FourDigitCardFormatWatcher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class CreditCardFragment extends Fragment {


    public CreditCardFragment() {
        // Required empty public constructor
    }

    public static CreditCardFragment newInstance() {
        CreditCardFragment fragment = new CreditCardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_credit_card, container, false);
        Spinner spinnerYear = v.findViewById(R.id.spCreditCardExpYear);
        List<String> yearsList = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i < currentYear + 20; i++) {
            yearsList.add(String.valueOf(i));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, yearsList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(arrayAdapter);

        EditText etCreditCard = v.findViewById(R.id.etCreditCardCard);
        etCreditCard.addTextChangedListener(new FourDigitCardFormatWatcher());
        return v;
    }
}