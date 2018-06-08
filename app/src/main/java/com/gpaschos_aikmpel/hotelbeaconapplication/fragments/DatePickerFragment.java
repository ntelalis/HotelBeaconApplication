package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Objects;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String minimumDate_KEY = "minDate";
    public static final String maximumDate_KEY = "maxDate";
    public static final String TAG = "datePickerFragment";
    public static final String EditTextType = "type";

    public static final int etArrival = 0;
    public static final int etDeparture = 1;

    private DateSelected dateSelected;

    private int etType;

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        dateSelected.pickedDate(etType, i2, i1, i);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dateSelected = (DateSelected) getActivity();

        Bundle bundle = getArguments();

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), this, year, month, day);

        if (bundle != null) {
            long minDate = bundle.getLong(minimumDate_KEY);
            long maxDate = bundle.getLong(maximumDate_KEY);
            etType = bundle.getInt(EditTextType);
            datePickerDialog.getDatePicker().setMinDate(minDate);
            datePickerDialog.getDatePicker().setMaxDate(maxDate);
        }

        return datePickerDialog;
    }

    public interface DateSelected {
        void pickedDate(int type, int day, int month, int year);
    }
}
