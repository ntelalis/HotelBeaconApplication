package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.alert;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class ProgressBarDialog extends DialogFragment {

    public static final String TAG = ProgressBarDialog.class.getSimpleName();

    public ProgressBarDialog(){

    }

    public static ProgressBarDialog newInstance() {
        ProgressBarDialog fragment = new ProgressBarDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
