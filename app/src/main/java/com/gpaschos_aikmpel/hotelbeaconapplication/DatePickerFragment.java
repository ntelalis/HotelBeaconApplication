package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Desktop on 7/3/2018.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    DateSelected dateSelected;
    public static final String ET_TYPE = "type";
    public static final int etArrival = 0;
    public static final int etDeparture = 1;
    private int etType;
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        dateSelected.pickedDate(etType,i2, i1, i);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dateSelected = (DateSelected) getActivity();

        Bundle bundle = getArguments();
        long minDate = bundle.getLong("minDate");
        long maxDate = bundle.getLong("maxDate");
        etType = bundle.getInt(ET_TYPE);

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog=  new DatePickerDialog(getActivity(), this, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(minDate);
        datePickerDialog.getDatePicker().setMaxDate(maxDate);
        return datePickerDialog;
    }

    public interface DateSelected{
        public void pickedDate(int type, int day, int month, int year);
    }
}
